package com.mrunknown404.dvz.items;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;
import com.mrunknown404.dvz.util.handlers.ConfigHandler;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ItemCreeperExplode extends ItemBase {

	private boolean didUse;
	private EntityPlayer p;
	
	public ItemCreeperExplode(String name, CreativeTabs tab, String tooltip, int maxStack) {
		super(name, tab, tooltip, maxStack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (p == null) {
			return;
		}
		
		BlockPos pos = p.getPosition();
		if (p.getCooldownTracker().getCooldown(this, 0f) == 0f && didUse == true) {
			if (p.isDead) {
				return;
			}
			
			if (!world.isRemote) {
				explode(world, pos);
				if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.supercreeper) {
					List<EntityLivingBase> entities = p.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(p.posX - 8, p.posY - 8, p.posZ - 8, p.posX + 8, p.posY + 8, p.posZ + 8));
					Explosion ex = new Explosion(p.getEntityWorld(), p, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ(), 4.5f, true, true);
					float f3 = 4.5f * 2.0F;
					
					for (EntityLivingBase e : entities) {
						if (e != p) {
							double d5 = e.posX - ex.getPosition().xCoord;
							double d7 = e.posY + (double) e.getEyeHeight() - ex.getPosition().yCoord;
							double d9 = e.posZ - ex.getPosition().zCoord;
							
							double d12 = e.getDistance(ex.getPosition().xCoord, ex.getPosition().yCoord, ex.getPosition().zCoord) / f3;
							double d14 = (double) world.getBlockDensity(ex.getPosition(), e.getEntityBoundingBox());
							double d10 = (1.0D - d12) * d14;
							
							e.attackEntityFrom(DamageSource.MAGIC, (float) ((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D));
							e.motionX += d5 * 2;
							e.motionY += d7 * 2;
							e.motionZ += d9 * 2;
						}
					}
				} else {
					List<EntityLivingBase> entities = p.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(p.posX - 4, p.posY - 4, p.posZ - 4, p.posX + 4, p.posY + 4, p.posZ + 4));
					Explosion ex = new Explosion(p.getEntityWorld(), p, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ(), 3.0f, false, true);
					float f3 = 3.0f * 2.0F;
					
					for (EntityLivingBase e : entities) {
						if (e != p) {
							double d5 = e.posX - ex.getPosition().xCoord;
							double d7 = e.posY + (double) e.getEyeHeight() - ex.getPosition().yCoord;
							double d9 = e.posZ - ex.getPosition().zCoord;
							
							double d12 = e.getDistance(ex.getPosition().xCoord, ex.getPosition().yCoord, ex.getPosition().zCoord) / f3;
							double d14 = (double) world.getBlockDensity(ex.getPosition(), e.getEntityBoundingBox());
							double d10 = (1.0D - d12) * d14;
							
							e.attackEntityFrom(DamageSource.MAGIC, (float) ((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D) / 4);
							e.motionX += d5;
							e.motionY += d7;
							e.motionZ += d9;
						}
					}
				}
				didUse = false;
				p.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE);
			}
		}
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		p = player;
		
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
			player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1.0f, 1.0f);
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.supercreeper) {
				player.getCooldownTracker().setCooldown(this, ConfigHandler.SuperCreeperExplodeTime);
			} else {
				player.getCooldownTracker().setCooldown(this, ConfigHandler.CreeperExplodeTime);
			}
			didUse = true;
		}
		
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		p = player;
		if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
			player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1.0f, 1.0f);
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.supercreeper) {
				player.getCooldownTracker().setCooldown(this, ConfigHandler.SuperCreeperExplodeTime);
			} else {
				player.getCooldownTracker().setCooldown(this, ConfigHandler.CreeperExplodeTime);
			}
			didUse = true;
		}
		
		return super.onItemRightClick(world, player, hand);
	}
	
	private void explode(World world, BlockPos pos) {
		if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.supercreeper) {
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
				} else if (world.getBlockState(_pos) == ModBlocks.STONECAKE.getDefaultState()) {
					world.setBlockState(_pos, Blocks.AIR.getDefaultState());
				} else if (world.getBlockState(_pos) == Blocks.TORCH.getDefaultState()) {
					world.setBlockState(_pos, Blocks.AIR.getDefaultState());
				}
			}
		} else {
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
				} else if (world.getBlockState(_pos) == ModBlocks.STONECAKE.getDefaultState()) {
					world.setBlockState(_pos, Blocks.AIR.getDefaultState());
				} else if (world.getBlockState(_pos) == Blocks.TORCH.getDefaultState()) {
					world.setBlockState(_pos, Blocks.AIR.getDefaultState());
				}
			}
		}
	}
}
