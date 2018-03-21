package com.mrunknown404.dvz.items;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

import com.mrunknown404.dvz.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDwarvenLongBow extends ItemBase {

	public ItemDwarvenLongBow(String name, CreativeTabs tab, String tooltip) {
		super(name, tab, tooltip);
		setNoRepair();
		
		this.maxStackSize = 1;
		
		addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn == null ? 0.0F : (entityIn.getActiveItemStack().getItem() != ModItems.DWARVEN_LONGBOW ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / (20.0F * 1.5f));
			}
		});
		addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		World world = player.getEntityWorld();
		
		if (player.experienceLevel >= 25) {
			if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
				craftArrows(world, player, stack);
				player.experience = 0;
				player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
			}
			
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	private void craftArrows(World world, EntityPlayer player, ItemStack stack) {
		world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 1.0f, 1.0f);
		if (!world.isRemote) {
			EntityItem item = new EntityItem(world, player.posX, player.posY + 1, player.posZ, new ItemStack(Items.ARROW, ThreadLocalRandom.current().nextInt(8, 12)));
			item.setPickupDelay(10);
			item.motionY = 0 + ThreadLocalRandom.current().nextDouble(0.15, 0.25);
			item.motionX = 0 + ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
			item.motionZ = 0 + ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
			world.spawnEntity(item);
		}
		player.getCooldownTracker().setCooldown(this, 25);
		player.removeExperienceLevel(25);
	}
	
	private ItemStack findAmmo(EntityPlayer player) {
		if (isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if (isArrow(itemstack)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}
	
	protected boolean isArrow(ItemStack stack) {
		return stack.getItem() instanceof ItemArrow;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(entityplayer);
			
			int i = getMaxItemUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, entityplayer, i, !itemstack.isEmpty() || flag);
			
			if (i < 0) {
				return;
			}
			
			if (!itemstack.isEmpty() || flag) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(Items.ARROW);
				}
				float f = getArrowVelocity(i);
				
				if ((double)f >= 0.1D) {
					boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));
					
					if (!world.isRemote) {
						ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW));
						EntityArrow entityarrow = itemarrow.createArrow(world, itemstack, entityplayer);
						entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.5f, 1.0F);
						
						if (f == 1.0F) {
							entityarrow.setIsCritical(true);
						}
						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
						
						if (j > 0) {
							entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 2.75D);
						} else {
							entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 2.75D);
						}
						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
						
						if (k > 0) {
							entityarrow.setKnockbackStrength(k);
						}
						
						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
							entityarrow.setFire(100);
						
						}
						
						if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
							entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
						}
						world.spawnEntity(entityarrow);
					}
					world.playSound(entityplayer, entityplayer.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					
					if (!flag1 && !entityplayer.capabilities.isCreativeMode) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							entityplayer.inventory.deleteStack(itemstack);
						}
					}
				}
			}
		}
	}

	public static float getArrowVelocity(int charge) {
		float f = (float)charge / (20.0F * 1.5f);
		f = (f * f + f * 2.0F) / 3.0F;
		
		if (f > 1.0F) {
			f = 1.0F;
		}
		return f;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return (int) (72000 * 1.5f);
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		boolean flag = !this.findAmmo(player).isEmpty();
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, world, player, hand, flag);
		
		if (ret != null) {
			return ret;
		}

		if (!player.capabilities.isCreativeMode && !flag) {
			return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
		} else {
			player.setActiveHand(hand);
			return new ActionResult(EnumActionResult.SUCCESS, itemstack);
		}
	}

	public int getItemEnchantability() {
		return 1;
	}
}
