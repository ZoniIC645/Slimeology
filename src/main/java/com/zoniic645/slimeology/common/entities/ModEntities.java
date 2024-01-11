package com.zoniic645.slimeology.common.entities;

import com.zoniic645.slimeology.SlimeologyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SlimeologyMod.MODID);

	public static final RegistryObject<EntityType<SmartSlimeEntity>> SMART_SLIME =
			ENTITY_TYPES.register("smart_slime", () -> EntityType.Builder.<SmartSlimeEntity>of(SmartSlimeEntity::new, MobCategory.CREATURE)
					.sized(2.04F, 2.04F)
					.build("smart_slime"));

	public static void register(IEventBus eventBus){
		ENTITY_TYPES.register(eventBus);
	}
}
