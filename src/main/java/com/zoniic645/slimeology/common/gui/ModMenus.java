package com.zoniic645.slimeology.common.gui;

import com.zoniic645.slimeology.SlimeologyMod;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus{
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SlimeologyMod.MODID);

	public static final RegistryObject<MenuType<SmartSlimeMenu>> SMART_SLIME_MENU
			= MENUS.register("smart_slime_menu", () -> new MenuType(SmartSlimeMenu::new, FeatureFlags.DEFAULT_FLAGS));

	public static void register(IEventBus eventBus){MENUS.register(eventBus);}
}
