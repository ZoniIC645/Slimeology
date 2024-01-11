package com.zoniic645.slimeology.common.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SmartSlimeEntity extends PathfinderMob implements ContainerListener, HasCustomInventoryScreen{
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(SmartSlimeEntity.class, EntityDataSerializers.INT);
	public float targetSquish;
	public float squish;
	public float oSquish;
	private boolean wasOnGround;

	public SmartSlimeEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
		super(pEntityType, pLevel);
		this.refreshDimensions();
		this.moveControl = new SmartSlimeMoveControl(this);
	}
	public SmartSlimeEntity(Level level, double x, double y, double z){
		this(ModEntities.SMART_SLIME.get(), level);
		setPos(x, y, z);
	}
	public SmartSlimeEntity(Level level, Vec3 pos){this(level, pos.x, pos.y, pos.z);}

	public static AttributeSupplier.Builder createAttributes(){
		return Slime.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 30)
				.add(Attributes.MOVEMENT_SPEED, 0.25f)
				.add(Attributes.FOLLOW_RANGE, 25D);
	}

	@Override protected void registerGoals(){
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TemptGoal(this, 1.15, Ingredient.of(Items.SLIME_BALL), false));
		this.goalSelector.addGoal(2, new SmartSlimeEntity.SmartSlimeRandomDirectionGoal(this));
		this.goalSelector.addGoal(3, new SmartSlimeEntity.SmartSlimeKeepOnJumpingGoal(this));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
	}

	protected void defineSynchedData(){
		super.defineSynchedData();
		this.entityData.define(ID_SIZE, -1);
	}

	public void setSize(int pSize, boolean pResetHealth){
		int i = Mth.clamp(pSize, 1, 255);
		this.entityData.set(ID_SIZE, i);
		this.reapplyPosition();
		this.refreshDimensions();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(i*i));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F+0.1F*(float)i));
		if(pResetHealth){
			this.setHealth(this.getMaxHealth());
		}
		this.xpReward = i;
	}

	public int getSize(){
		return this.entityData.get(ID_SIZE);
	}

	public void addAdditionalSaveData(CompoundTag pCompound){
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Size", this.getSize()-1);
		pCompound.putBoolean("wasOnGround", this.wasOnGround);
	}

	public void readAdditionalSaveData(CompoundTag pCompound){
		this.setSize(pCompound.getInt("Size")+1, false);
		super.readAdditionalSaveData(pCompound);
		this.wasOnGround = pCompound.getBoolean("wasOnGround");
	}

	public boolean isTiny(){
		return this.getSize()<=1;
	}

	protected ParticleOptions getParticleType(){
		return ParticleTypes.ITEM_SLIME;
	}

	public void tick(){
		this.squish += (this.targetSquish-this.squish)*0.5F;
		this.oSquish = this.squish;
		super.tick();
		if(this.onGround()&&!this.wasOnGround){
			int i = this.getSize();

			// Forge: Don't spawn particles if it's handled by the implementation itself
			if(!spawnCustomParticles())
				for(int j = 0; j<i*8; ++j){
					float f = this.random.nextFloat()*((float)Math.PI*2F);
					float f1 = this.random.nextFloat()*0.5F+0.5F;
					float f2 = Mth.sin(f)*(float)i*0.5F*f1;
					float f3 = Mth.cos(f)*(float)i*0.5F*f1;
					this.level().addParticle(this.getParticleType(), this.getX()+(double)f2, this.getY(), this.getZ()+(double)f3, 0.0D, 0.0D, 0.0D);
				}

			this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat()-this.random.nextFloat())*0.2F+1.0F)/0.8F);
			this.targetSquish = -0.5F;
		}else if(!this.onGround()&&this.wasOnGround){
			this.targetSquish = 1.0F;
		}

		this.wasOnGround = this.onGround();
		this.decreaseSquish();
	}

	protected void decreaseSquish(){
		this.targetSquish *= 0.6F;
	}

	public void refreshDimensions(){
		double d0 = this.getX();
		double d1 = this.getY();
		double d2 = this.getZ();
		super.refreshDimensions();
		this.setPos(d0, d1, d2);
	}

	public EntityDimensions getDimensions(Pose pPose){
		return super.getDimensions(pPose).scale(0.255F*(float)this.getSize());
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey){
		if(ID_SIZE.equals(pKey)){
			this.refreshDimensions();
			this.setYRot(this.yHeadRot);
			this.yBodyRot = this.yHeadRot;
			if(this.isInWater()&&this.random.nextInt(20)==0){
				this.doWaterSplashEffect();
			}
		}

		super.onSyncedDataUpdated(pKey);
	}

	protected void jumpFromGround(){
		Vec3 vec3 = this.getDeltaMovement();
		this.setDeltaMovement(vec3.x, (double)this.getJumpPower(), vec3.z);
		this.hasImpulse = true;
	}

	protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
		return 0.625F*pSize.height;
	}

	protected float getAttackDamage(){
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	float getSoundPitch(){
		float f = this.isTiny() ? 1.4F : 0.8F;
		return ((this.random.nextFloat()-this.random.nextFloat())*0.2F+1.0F)*f;
	}

	protected SoundEvent getHurtSound(DamageSource pDamageSource){
		return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
	}
	protected SoundEvent getDeathSound(){
		return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
	}
	protected SoundEvent getSquishSound(){
		return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
	}
	protected SoundEvent getJumpSound(){
		return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
	}

	protected boolean doPlayJumpSound(){
		return this.getSize()>0;
	}

	protected int getJumpDelay(){
		return this.random.nextInt(20)+10;
	}

	public boolean removeWhenFarAway(double pDistanceToClosestPlayer){
		return false;
	}

	protected boolean spawnCustomParticles(){return false;}

	public void containerChanged(Container pContainer){}
	public void openCustomInventoryScreen(Player pPlayer){}

	static class SmartSlimeKeepOnJumpingGoal extends Goal{
		private final SmartSlimeEntity slime;

		public SmartSlimeKeepOnJumpingGoal(SmartSlimeEntity pSlime){
			this.slime = pSlime;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse(){
			return !this.slime.isPassenger();
		}
	}
	static class SmartSlimeRandomDirectionGoal extends Goal {
		private final SmartSlimeEntity slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public SmartSlimeRandomDirectionGoal(SmartSlimeEntity pSlime) {
			this.slime = pSlime;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof SmartSlimeEntity.SmartSlimeMoveControl;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
				this.chosenDegrees = (float)this.slime.getRandom().nextInt(360);
			}

			MoveControl movecontrol = this.slime.getMoveControl();
			if (movecontrol instanceof SmartSlimeEntity.SmartSlimeMoveControl slime$slimemovecontrol) {
				slime$slimemovecontrol.setDirection(this.chosenDegrees, false);
			}

		}
	}
	static class SmartSlimeMoveControl extends MoveControl{
		private float yRot;
		private int jumpDelay;
		private final SmartSlimeEntity smartSlime;
		private boolean isAggressive;

		public SmartSlimeMoveControl(SmartSlimeEntity pSmartSlime){
			super(pSmartSlime);
			this.smartSlime = pSmartSlime;
			this.yRot = 180.0F*pSmartSlime.getYRot()/(float)Math.PI;
		}

		public void setDirection(float pYRot, boolean pAggressive){
			this.yRot = pYRot;
			this.isAggressive = pAggressive;
		}

		public void setWantedMovement(double pSpeed){
			this.speedModifier = pSpeed;
			this.operation = MoveControl.Operation.MOVE_TO;
		}

		public void tick(){
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
			this.mob.yHeadRot = this.mob.getYRot();
			this.mob.yBodyRot = this.mob.getYRot();
			if(this.operation!=MoveControl.Operation.MOVE_TO){
				this.mob.setZza(0.0F);
			}else{
				this.operation = MoveControl.Operation.WAIT;
				if(this.mob.onGround()){
					this.mob.setSpeed((float)(this.speedModifier*this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if(this.jumpDelay--<=0){
						this.jumpDelay = this.smartSlime.getJumpDelay();
						if(this.isAggressive){
							this.jumpDelay /= 3;
						}

						this.smartSlime.getJumpControl().jump();
						if(this.smartSlime.doPlayJumpSound()){
							this.smartSlime.playSound(this.smartSlime.getJumpSound(), this.smartSlime.getSoundVolume(), this.smartSlime.getSoundPitch());
						}
					}else{
						this.smartSlime.xxa = 0.0F;
						this.smartSlime.zza = 0.0F;
						this.mob.setSpeed(0.0F);
					}
				}else{
					this.mob.setSpeed((float)(this.speedModifier*this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}

			}
		}
	}
}
