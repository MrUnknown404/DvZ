package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGlue extends ItemBase {

	private boolean isSuper = false;
	
	public ItemGlue(String name, CreativeTabs tab, String tooltip, boolean isSuper) {
		super(name, tab, tooltip);
		
		this.isSuper = isSuper;
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
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
			
			world.playSound(player, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			if (!world.isRemote) {
				if (isSuper == false) {
					player.getCooldownTracker().setCooldown(this, 5);
				} else {
					player.getCooldownTracker().setCooldown(this, 10);
				}
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
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
		
		world.playSound(player, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
		if (!world.isRemote) {
			if (isSuper == false) {
				player.getCooldownTracker().setCooldown(this, 5);
			} else {
				player.getCooldownTracker().setCooldown(this, 10);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
}
