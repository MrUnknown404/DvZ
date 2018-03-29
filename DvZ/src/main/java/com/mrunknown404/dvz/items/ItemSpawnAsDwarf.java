package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumDwarfType;

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

public class ItemSpawnAsDwarf extends ItemBase {

	private int time;
	private boolean didUse = false;
	private EnumDwarfType enumDwarfType;
	private EntityPlayer p;
	
	public ItemSpawnAsDwarf(String name, CreativeTabs tab, String tooltip, int maxStack, EnumDwarfType type, int time) {
		super(name, tab, tooltip, maxStack);
		
		enumDwarfType = type;
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
			GameManager.setupPlayerDwarf(p, enumDwarfType);
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		p = player;
		
		if (!player.getEntityWorld().isRemote) {
			if (player.getEntityWorld().getScoreboard().getTeam("dwarves") != null) {
				if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
					player.getCooldownTracker().setCooldown(this, time);
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.PLAYERS, 1.0f, 1.0f);
					didUse = true;
				}
			} else {
				p.sendMessage(new TextComponentString("Game has not started!"));
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
		
		if (world.getScoreboard().getTeam("dwarves") != null) {
			if (player.getCooldownTracker().getCooldown(this, 1f) == 0f) {
				player.getCooldownTracker().setCooldown(this, time);
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.PLAYERS, 1.0f, 1.0f);
				didUse = true;
			}
		}else {
			p.sendMessage(new TextComponentString("Game has not started!"));
		}
		return super.onItemRightClick(world, player, hand);
	}
}
