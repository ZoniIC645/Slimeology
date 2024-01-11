package com.zoniic645.slimeology.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zoniic645.slimeology.SlimeologyMod;
import com.zoniic645.slimeology.client.models.ModModelLayers;
import com.zoniic645.slimeology.client.models.SmartSlimeEntityModel;
import com.zoniic645.slimeology.client.models.SmartSlimeOuterLayer;
import com.zoniic645.slimeology.common.entities.SmartSlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SmartSlimeEntityRenderer extends MobRenderer<SmartSlimeEntity, SmartSlimeEntityModel<SmartSlimeEntity>>{

	public SmartSlimeEntityRenderer(EntityRendererProvider.Context pContext){
		super(pContext, new SmartSlimeEntityModel<>(pContext.bakeLayer(ModModelLayers.SMART_SLIME_INNER_LAYER)), 0.25f);
		this.addLayer(new SmartSlimeOuterLayer<>(this, pContext.getModelSet()));
	}

	@Override public void render(SmartSlimeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
		this.shadowRadius = 0.25F*(float)pEntity.getSize();
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
	}

	@Override protected void scale(SmartSlimeEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime){
		float f = 0.999F;
		pPoseStack.scale(0.999F, 0.999F, 0.999F);
		pPoseStack.translate(0.0F, 0.001F, 0.0F);
		float f1 = (float)pLivingEntity.getSize();
		float f2 = Mth.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish)/(f1*0.5F+1.0F);
		float f3 = 1.0F/(f2+1.0F);
		pPoseStack.scale(f3*f1, 1.0F/f3*f1, f3*f1);
	}
	@Override public ResourceLocation getTextureLocation(SmartSlimeEntity pEntity){
		return new ResourceLocation(SlimeologyMod.MODID, "textures/entity/smart_slime.png");
	}
}
