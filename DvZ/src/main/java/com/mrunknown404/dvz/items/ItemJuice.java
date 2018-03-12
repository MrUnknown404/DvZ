package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemJuice extends ItemBase {

	/** String name, CreativeTabs tab */
	public ItemJuice(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		setMaxStackSize(1);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		World world = player.getEntityWorld();
		
		if (player.experienceLevel >= 75) {
			if (player.getHealth() != 20f) {
				if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
					healPlayer(world, player);
					player.experience = 0;
					player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
				}
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.experienceLevel >= 75) {
			if (player.getHealth() != 20f) {
				healPlayer(world, player);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	private void healPlayer(World world, EntityPlayer player) {
		if (!world.isRemote) {
			world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);
			player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
			player.heal(player.getMaxHealth());
		}
		player.getCooldownTracker().setCooldown(this, 10);
		player.getFoodStats().addStats(4, 0f);
		player.removeExperienceLevel(75);
	}
}
