package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumMonsterType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSpawnAsMonster extends ItemBase {

	private float time;
	private boolean didUse = false;
	private EnumMonsterType enumMonsterType;
	
	public ItemSpawnAsMonster(String name, CreativeTabs tab, String tooltip, EnumMonsterType type, float time) {
		super(name, tab, tooltip);
		
		enumMonsterType = type;
		this.time = time;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		EntityPlayer player = (EntityPlayer) entity;

		if (player.getEntityWorld().isRemote) {
			return;
		}
		
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f && didUse == true) {
			if (world.getScoreboard().getTeam("monsters") != null) {
				didUse = false;
				GameManager.setupPlayerMonster(player, enumMonsterType);
			} else {
				player.sendMessage(new TextComponentString("Monsters have not been released"));
			}
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		
		if (player.getEntityWorld().isRemote) {
			return super.onEntitySwing(entityLiving, stack);
		}
		
		if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
			player.getCooldownTracker().setCooldown(this, (int) time);
			didUse = true;
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getEntityWorld().isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		
		if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
			player.getCooldownTracker().setCooldown(this, (int) time);
			didUse = true;
		}
		return super.onItemRightClick(world, player, hand);
	}
}
