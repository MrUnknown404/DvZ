package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDragonTalons extends ItemBase{

	private boolean didUse;
	private EntityPlayer player;
	private EntityLivingBase target;
	
	public ItemDragonTalons(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		setMaxStackSize(1);
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
			target.setPosition(player.posX, player.posY, player.posZ);
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
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
			tempBool = true;
			didUse = true;
		}
		
		return super.itemInteractionForEntity(stack, player, target, hand);
	}
}
