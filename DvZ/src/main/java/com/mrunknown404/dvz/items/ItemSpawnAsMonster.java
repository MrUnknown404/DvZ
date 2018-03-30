package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.handlers.ConfigHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSpawnAsMonster extends ItemBase {

	private boolean didUse = false;
	private EnumMonsterType enumMonsterType;
	private EntityPlayer p;
	
	public ItemSpawnAsMonster(String name, CreativeTabs tab, String tooltip, int maxStack, EnumMonsterType type) {
		super(name, tab, tooltip, maxStack);
		
		enumMonsterType = type;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (p == null) {
			return;
		} else if (p.getEntityWorld().isRemote) {
			return;
		}
		
		if (p.getCooldownTracker().getCooldown(this, 0f) == 0f && didUse == true) {
			didUse = false;
			GameManager.setupPlayerMonster(p, enumMonsterType, null);
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		p = player;
		
		if (!player.getEntityWorld().isRemote) {
			if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
				if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
					player.getCooldownTracker().setCooldown(this, ConfigHandler.SpawnAsMonsterTime);
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.HOSTILE, 1.0f, 1.0f);
					didUse = true;
				}
			} else {
				p.sendMessage(new TextComponentString("Monsters have not been released"));
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		p = player;
		if (player.getEntityWorld().isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		
		if (world.getScoreboard().getTeam("monsters") != null) {
			if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
				player.getCooldownTracker().setCooldown(this, ConfigHandler.SpawnAsMonsterTime);
				didUse = true;
			}
		}else {
			p.sendMessage(new TextComponentString("Monsters have not been released"));
		}
		return super.onItemRightClick(world, player, hand);
	}
}
