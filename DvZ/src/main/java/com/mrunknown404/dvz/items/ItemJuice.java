package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemJuice extends ItemBase {

	public ItemJuice(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		setMaxStackSize(1);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		
		if (player.experienceLevel >= 100) {
			if (player.getHealth() != player.getMaxHealth()) {
				if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
					healPlayer(player);
				}
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.experienceLevel >= 100) {
			if (player.getHealth() != player.getMaxHealth()) {
				healPlayer(player);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	private void healPlayer(EntityPlayer player) {
		if (player.getEntityWorld().isRemote) {
			player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
			player.heal(player.getMaxHealth());
		}
		player.getCooldownTracker().setCooldown(this, 10);
		player.getFoodStats().addStats(4, 0f);
		player.removeExperienceLevel(100);
		
		player.experience = 0;
		player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
	}
}
