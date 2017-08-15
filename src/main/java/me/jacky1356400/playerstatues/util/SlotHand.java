/**
 * Slot class for the slot hand
 */

package me.jacky1356400.playerstatues.util;

import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotHand extends Slot {

	public SlotHand(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	public SlotHand(TileEntityStatue te, int i, int j, int k) {
		super(te, i, j, k);
	}

}