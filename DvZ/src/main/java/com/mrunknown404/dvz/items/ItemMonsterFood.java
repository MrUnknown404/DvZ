package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMonsterFood extends ItemBase {

	public ItemMonsterFood(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
			player.getFoodStats().setFoodLevel(19);
			player.getCooldownTracker().setCooldown(this, 30 * 20);
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.getFoodStats().setFoodLevel(19);
		player.getCooldownTracker().setCooldown(this, 30 * 20);
		
		return super.onItemRightClick(world, player, hand);
	}
}
