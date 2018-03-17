package com.mrunknown404.dvz.items;

import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCreeperExplode extends ItemBase {

	private boolean isSuper;
	private boolean didUse = false;
	private EntityPlayer p;
	
	public ItemCreeperExplode(String name, CreativeTabs tab, String tooltip, boolean isSuper) {
		super(name, tab, tooltip);
		setMaxStackSize(1);
		
		this.isSuper = isSuper;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (p == null) {
			return;
		}
		
		BlockPos pos = p.getPosition();
		if (p.getCooldownTracker().getCooldown(this, 0f) == 0f && didUse == true) {
			if (isSuper) {
				if (!world.isRemote) {
					for (BlockPos _pos : pos.getAllInBox(pos.add(-8, -8, -8), pos.add(8, 8, 8))) {
						if (world.getBlockState(_pos) == ModBlocks.HARDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
								world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
								world.setBlockState(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.DWARVENSTONE.getDefaultState(), ModBlocks.DWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.DWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
								world.setBlockState(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
								world.setBlockState(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState(), ModBlocks.SOFTDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 3) == 0) {
								world.setBlockState(_pos, Blocks.AIR.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 6) == 0) {
							} else {
								world.setBlockState(_pos, Blocks.AIR.getDefaultState());
								world.notifyBlockUpdate(_pos, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 0);
							}
						}
					}
				}
			} else {
				if (!world.isRemote) {
					for (BlockPos _pos : pos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
						if (world.getBlockState(_pos) == ModBlocks.HARDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 7) == 0) {
								world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDHARDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 7) == 0) {
								world.setBlockState(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.DWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.DWARVENSTONE.getDefaultState(), ModBlocks.DWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.DWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 7) == 0) {
								world.setBlockState(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 7) == 0) {
								world.setBlockState(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.SOFTDWARVENSTONE.getDefaultState(), ModBlocks.SOFTDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.SOFTDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
								world.setBlockState(_pos, Blocks.AIR.getDefaultState());
							} else {
								world.setBlockState(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState());
							}
							world.notifyBlockUpdate(_pos, ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState(), ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState(), 0);
						} else if (world.getBlockState(_pos) == ModBlocks.CRACKEDSOFTDWARVENSTONE.getDefaultState()) {
							if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
							} else {
								world.setBlockState(_pos, Blocks.AIR.getDefaultState());
								world.notifyBlockUpdate(_pos, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 0);
							}
						}
					}
				}
			}
			if (!world.isRemote) {
				if (isSuper) {
					world.createExplosion(p, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ(), 4.5f, true);
				} else {
					world.createExplosion(p, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ(), 3.0f, true);
				}
				killPlayer(p);
			}
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	public void killPlayer(EntityPlayer e) {
		didUse = false;
		e.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
			if (!player.getEntityWorld().isRemote) {
				player.getEntityWorld().playSound((EntityPlayer)null, player.getPosition(), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.PLAYERS, 1.0f, 1.0f);
				player.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
			}
			if (isSuper) {
				player.getCooldownTracker().setCooldown(this, 5 * 20);
			} else {
				player.getCooldownTracker().setCooldown(this, 3 * 20);
			}
			didUse = true;
		}
		
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
			if (!player.getEntityWorld().isRemote) {
				player.getEntityWorld().playSound((EntityPlayer)null, player.getPosition(), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.PLAYERS, 1.0f, 1.0f);
				player.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
			}
			if (isSuper) {
				player.getCooldownTracker().setCooldown(this, 5 * 20);
			} else {
				player.getCooldownTracker().setCooldown(this, 3 * 20);
			}
			didUse = true;
		}
		
		return super.onItemRightClick(world, player, hand);
	}
}
