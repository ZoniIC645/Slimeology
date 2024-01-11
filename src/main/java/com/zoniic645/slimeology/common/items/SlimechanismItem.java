package com.zoniic645.slimeology.common.items;

import com.zoniic645.slimeology.common.entities.SmartSlimeEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlimechanismItem extends Item{
	public SlimechanismItem(Properties pProperties){
		super(pProperties);
	}

	@Override public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand){
		if(pPlayer.level().isClientSide)
			return InteractionResult.FAIL;
		if(!(pInteractionTarget instanceof Slime))
			return InteractionResult.PASS;

		SmartSlimeEntity smartSlime = new SmartSlimeEntity(pPlayer.level(), pInteractionTarget.position());
		pPlayer.level().addFreshEntity(smartSlime);
		smartSlime.setSize(((Slime)pInteractionTarget).getSize(),true);
		pInteractionTarget.discard();
		return InteractionResult.PASS;
	}
}
