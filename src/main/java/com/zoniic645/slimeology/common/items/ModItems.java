package com.zoniic645.slimeology.common.items;

import com.zoniic645.slimeology.SlimeologyMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SlimeologyMod.MODID);

	public static final RegistryObject<Item> SLIME_CORE = ITEMS.register("slime_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SLIMECHANISM = ITEMS.register("slimechanism", () -> new SlimechanismItem(new Item.Properties()));

	public static void register(IEventBus eventBus){
		ITEMS.register(eventBus);
	}
}
