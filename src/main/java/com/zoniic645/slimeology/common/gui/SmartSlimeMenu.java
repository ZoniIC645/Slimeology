package com.zoniic645.slimeology.common.gui;

import com.zoniic645.slimeology.common.entities.SmartSlimeEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SmartSlimeMenu extends AbstractContainerMenu{
	private final Container slimeContainer;
	private final SmartSlimeEntity slime;

	protected SmartSlimeMenu(int pContainerId, Inventory pPlayerInventory, Container pContainer, final SmartSlimeEntity pSlime){
		super((MenuType<?>)null, pContainerId);
		this.slimeContainer = pContainer;
		this.slime = pSlime;
		pContainer.startOpen(pPlayerInventory.player);
	}

	public ItemStack quickMoveStack(Player pPlayer, int pIndex){
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		return null;
	}
	public boolean stillValid(Player pPlayer) {return this.slime.distanceTo(pPlayer) < 8.0F;}
}
