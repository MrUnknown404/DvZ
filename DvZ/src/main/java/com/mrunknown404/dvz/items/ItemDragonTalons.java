package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDragonTalons extends ItemBase {

	private boolean didUse;
	private EntityPlayer player;
	private EntityLivingBase target;
	
	public ItemDragonTalons(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		setMaxStackSize(1);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		entity.onKillCommand();
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (target == null || player == null) {
			return;
		} else if (player.getEntityWorld().isRemote) {
			return;
		}
		
		if (didUse == true) {
			if (target.isDead) {
				didUse = false;
				target = null;
				return;
			}
			target.fallDistance = 0;
			target.setPositionAndUpdate(player.posX, player.posY - 2d, player.posZ);
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.WEATHER, 5.0f, 1.0f);
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	private boolean tempBool;
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) {
			return super.onItemRightClick(world, player, hand);
		} else if (target == null) {
			return super.onItemRightClick(world, player, hand);
		}
		
		if (didUse == true && tempBool == false) {
			player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.WEATHER, 1.0f, 1.0f);
			didUse = false;
			target = null;
		}
		tempBool = false;
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		this.player = player;
		this.target = target;
		
		if (player.getEntityWorld().isRemote) {
			return super.itemInteractionForEntity(stack, player, target, hand);
		}
		
		if (didUse == false) {
			player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.WEATHER, 1.0f, 1.0f);
			tempBool = true;
			didUse = true;
		}
		
		return super.itemInteractionForEntity(stack, player, target, hand);
	}
}
