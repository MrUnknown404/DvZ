package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGlue extends ItemBase {

	private boolean isSuper = false;
	
	/** String name, CreativeTabs tab, String tooltip, boolean isSuper */
	public ItemGlue(String name, CreativeTabs tab, String tooltip, boolean isSuper) {
		super(name, tab, tooltip);
		
		if (isSuper == true) {
			this.isSuper = isSuper;
		}
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		
		if (isSuper == false) {
			for (BlockPos _pos : pos.getAllInBox(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
				if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()) {
					world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
					world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.DWARVENSTONE.getDefaultState(), 0);
				}
			}
		} else {
			for (BlockPos _pos : pos.getAllInBox(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
				if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.DWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState()) {
					world.setBlockState(_pos, ModBlocks.HARDDWARVENSTONE.getDefaultState());
					world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.HARDDWARVENSTONE.getDefaultState(), 0);
				}
			}
		}
		
		ItemStack itemstack = player.getHeldItemMainhand();
		
		if (!player.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}
		
		if (!world.isRemote) {
			world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
			if (player instanceof EntityPlayer) {
				if (isSuper == false) {
					((EntityPlayer)player).getCooldownTracker().setCooldown(this, 5);
				} else {
					((EntityPlayer)player).getCooldownTracker().setCooldown(this, 10);
				}
			}
		}
		
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (isSuper == false) {
			for (BlockPos _pos : player.getPosition().getAllInBox(player.getPosition().add(-2, -2, -2), player.getPosition().add(2, 2, 2))) {
				if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()) {
					world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
					world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.DWARVENSTONE.getDefaultState(), 0);
				}
			}
		} else {
			for (BlockPos _pos : player.getPosition().getAllInBox(player.getPosition().add(-2, -2, -2), player.getPosition().add(2, 2, 2))) {
				if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.DWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState()) {
					world.setBlockState(_pos, ModBlocks.HARDDWARVENSTONE.getDefaultState());
					world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.HARDDWARVENSTONE.getDefaultState(), 0);
				}
			}
		}
		
		ItemStack itemstack = player.getHeldItem(hand);
		
		if (!player.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}
		
		if (!world.isRemote) {
			world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
			if (player instanceof EntityPlayer) {
				if (isSuper == false) {
					((EntityPlayer)player).getCooldownTracker().setCooldown(this, 5);
				} else {
					((EntityPlayer)player).getCooldownTracker().setCooldown(this, 10);
				}
			}
		}
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
}
