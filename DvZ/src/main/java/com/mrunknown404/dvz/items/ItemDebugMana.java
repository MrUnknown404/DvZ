package com.mrunknown404.dvz.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDebugMana extends ItemBase {

	//** String name, CreativeTabs tab, String tooltip, */
	public ItemDebugMana(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		if (!world.isRemote) {
			List<EntityPlayer> p = world.playerEntities;
			
			for (EntityPlayer _p : p) {
				_p.addExperienceLevel(250);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
}
