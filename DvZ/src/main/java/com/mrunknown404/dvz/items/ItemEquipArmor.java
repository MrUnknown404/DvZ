package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEquipArmor extends ItemBase {

	public ItemEquipArmor(String name, CreativeTabs tab, String tooltip, int maxStack) {
		super(name, tab, tooltip, maxStack);
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		//do stuff
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
