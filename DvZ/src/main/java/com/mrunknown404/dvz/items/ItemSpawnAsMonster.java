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

	private int time;
	private boolean didUse = false;
	private EnumMonsterType enumMonsterType;
	private EntityPlayer p;
	
	public ItemSpawnAsMonster(String name, CreativeTabs tab, String tooltip, EnumMonsterType type, int time) {
		super(name, tab, tooltip);
		
		enumMonsterType = type;
		this.time = time;
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
			GameManager.setupPlayerMonster(p, enumMonsterType);
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		p = player;
		
		if (player.getEntityWorld().isRemote) {
			return super.onEntitySwing(entityLiving, stack);
		}
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
				player.getCooldownTracker().setCooldown(this, time);
				didUse = true;
			}
		} else {
			p.sendMessage(new TextComponentString("Monsters have not been released"));
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
				player.getCooldownTracker().setCooldown(this, time);
				didUse = true;
			}
		}else {
			p.sendMessage(new TextComponentString("Monsters have not been released"));
		}
		return super.onItemRightClick(world, player, hand);
	}
}
