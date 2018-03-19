package com.mrunknown404.dvz.items;

import java.util.List;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemArmorBase extends ItemArmor implements IHasModel {

	private String tooltip;
	
	public ItemArmorBase(String name, CreativeTabs tab, String tooltip, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setNoRepair();
		
		this.tooltip = tooltip;
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if (this.tooltip != "") {
			tooltip.add(this.tooltip);
		}
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		World world = player.getEntityWorld();
		
		if (player.experienceLevel >= 25) {
			if (getMaxDamage(stack) != getDamage(stack) + getMaxDamage(stack)) {
				if (player.getCooldownTracker().getCooldown(this, 0f) == 0f) {
					repairArmor(world, player, stack);
					player.experience = 0;
					player.addExperience((int) (player.xpBarCap() * (player.experienceLevel * 0.001f)));
				}
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	private void repairArmor(World world, EntityPlayer player, ItemStack stack) {
		world.playSound(player, player.getPosition(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
		player.getCooldownTracker().setCooldown(this, 20);
		player.removeExperienceLevel(25);
		setDamage(stack, getDamage(stack) - (getMaxDamage(stack) / 5));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
