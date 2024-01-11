package com.zoniic645.slimeology.client;

import com.zoniic645.slimeology.SlimeologyMod;
import com.zoniic645.slimeology.common.entities.ModEntities;
import com.zoniic645.slimeology.client.models.ModModelLayers;
import com.zoniic645.slimeology.client.models.SmartSlimeEntityModel;
import com.zoniic645.slimeology.client.render.SmartSlimeEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SlimeologyMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
public class ModEventBusClientEvents{
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
		event.registerLayerDefinition(ModModelLayers.SMART_SLIME_INNER_LAYER, SmartSlimeEntityModel::createInnerBodyLayer);
		event.registerLayerDefinition(ModModelLayers.SMART_SLIME_OUTER_LAYER, SmartSlimeEntityModel::createOuterBodyLayer);
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		EntityRenderers.register(ModEntities.SMART_SLIME.get(), SmartSlimeEntityRenderer::new);
	}
}
