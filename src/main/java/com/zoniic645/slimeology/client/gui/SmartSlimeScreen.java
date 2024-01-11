package com.zoniic645.slimeology.client.gui;

import com.zoniic645.slimeology.SlimeologyMod;
import com.zoniic645.slimeology.common.entities.SmartSlimeEntity;
import com.zoniic645.slimeology.common.gui.SmartSlimeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SmartSlimeScreen extends AbstractContainerScreen<SmartSlimeMenu>{
	private static final ResourceLocation SMARTSLIME_INVENTORY_LOCATION = new ResourceLocation(SlimeologyMod.MODID, "textures/gui/container/smartslime.png");
	private final SmartSlimeEntity slime;
	private float xMouse;
	private float yMouse;

	public SmartSlimeScreen(SmartSlimeMenu pMenu, Inventory pPlayerInventory, SmartSlimeEntity pSlime){
		super(pMenu, pPlayerInventory, pSlime.getDisplayName());
		this.slime=pSlime;
	}

	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY){
	}

	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
		super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
	}
}
