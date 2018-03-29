package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.util.EnumDragonType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDragonBreath extends ItemBase {

	private EntityFallingBlock block;
	private EntityPlayer p;
	private EnumDragonType type;
	
	public ItemDragonBreath(String name, CreativeTabs tab, String tooltip, int maxStack, EnumDragonType type) {
		super(name, tab, tooltip, maxStack);
		this.type = type;
	}

	private int tick = 0;
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (p == null) {
			return;
		} else if (tick < 3) {
			tick++;
			return;
		}
		
		tick = 0;
		
		if (p.getCooldownTracker().getCooldown(this, 0f) != 0f && p.getHeldItem(p.getActiveHand()).getItem() == this) {
			if (!p.getEntityWorld().isRemote) {
				p.getEntityWorld().playSound(null, p.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.WEATHER, 2.0f, 0.25f);
			}
			
			setupBlock(p.getEntityWorld());
			world.spawnEntity(block);
		} else if (p.getCooldownTracker().getCooldown(this, 0f) != 0f && p.getHeldItem(p.getActiveHand()).getItem() != this) {
			p.getCooldownTracker().setCooldown(this, 0);
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	private void setupBlock(World world) {
		double yaw = Math.toRadians(p.rotationYaw);
		double pitch = Math.toRadians(p.rotationPitch);
		double xDirection = -Math.sin(yaw) * Math.cos(pitch);
		double yDirection = -Math.sin(pitch);
		double zDirection = Math.cos(yaw) * Math.cos(pitch);
		
		if (type == EnumDragonType.vlarunga) {
			block = new EntityFallingBlock(world, p.posX, p.posY + p.getDefaultEyeHeight() - 0.5, p.posZ, Blocks.FIRE.getDefaultState());
			block.fallTime = 1;
			block.shouldDropItem = false;
			block.fallDistance = 1000;
			block.entityCollisionReduction = 100;
			block.setHurtEntities(true);
			block.motionX = xDirection * 1.5;
			block.motionY = yDirection * 1.5;
			block.motionZ = zDirection * 1.5;
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getEntityWorld().isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		p = player;
		player.getCooldownTracker().setCooldown(this, 3 * 20);
		return super.onItemRightClick(world, player, hand);
	}
}
