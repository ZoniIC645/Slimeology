package com.zoniic645.slimeology;

import com.zoniic645.slimeology.common.entities.ModEntities;
import com.zoniic645.slimeology.common.gui.ModMenus;
import com.zoniic645.slimeology.common.items.ModCreateModeTabs;
import com.zoniic645.slimeology.common.items.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SlimeologyMod.MODID)
public class SlimeologyMod{
	public static final String MODID = "slimeology";

	public SlimeologyMod(){
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.register(modEventBus);
		ModEntities.register(modEventBus);
		ModCreateModeTabs.register(modEventBus);
		ModMenus.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}
}
