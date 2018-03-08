package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGlue extends ModItemBase {

	private boolean isSuper = false;
	
	/** String name, CreativeTabs tab, boolean isSuper */
	public ItemGlue(String name, CreativeTabs tab, boolean isSuper) {
		super(name, tab);
		
		if (isSuper == true) {
			this.isSuper = isSuper;
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (isSuper == false) {
			for (BlockPos _pos : pos.getAllInBox(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
				if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()
						| world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()) {
					world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());		
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
				}
			}
		}
		
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}
		
		if (!world.isRemote) {
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