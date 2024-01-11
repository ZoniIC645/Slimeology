package com.zoniic645.slimeology.common.items;

import com.zoniic645.slimeology.SlimeologyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreateModeTabs{
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SlimeologyMod.MODID);

	public static final RegistryObject<CreativeModeTab> SLIMEOGLOY_TAB = CREATIVE_MODE_TABS.register("slimeology_tab",
			()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.SLIMECHANISM.get()))
					.title(Component.translatable("creativetab.slimeology_tab"))
					.displayItems(((p_270258_, p_259752_) -> {
						p_259752_.accept(ModItems.SLIMECHANISM.get());
						p_259752_.accept(ModItems.SLIME_CORE.get());
					}))
					.build());

	public static void register(IEventBus eventBus){
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
