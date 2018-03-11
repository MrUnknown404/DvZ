package com.mrunknown404.dvz.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		player.removeExperienceLevel(250);
		player.experience = 0;
		player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.addExperienceLevel(250);
		player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
		return super.onItemRightClick(world, player, hand);
	}
}
