package com.zoniic645.slimeology.common.events;

import com.zoniic645.slimeology.SlimeologyMod;
import com.zoniic645.slimeology.common.entities.ModEntities;
import com.zoniic645.slimeology.common.entities.SmartSlimeEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeologyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventEventBus{
	@SubscribeEvent
	public static void entityAttribute(EntityAttributeCreationEvent event){
		event.put(ModEntities.SMART_SLIME.get(), SmartSlimeEntity.createAttributes().build());
	}
}
