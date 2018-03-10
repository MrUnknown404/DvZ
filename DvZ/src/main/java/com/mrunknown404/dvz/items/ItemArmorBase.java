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
	private int tick = 0;
	
	/** String name, CreativeTabs tab, String tooltip, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot */
	public ItemArmorBase(String name, CreativeTabs tab, String tooltip, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
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
				repairArmor(world, player, stack);
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	private void repairArmor(World world, EntityPlayer player, ItemStack stack) {
		if (!world.isRemote) {
			world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
			player.playSound(SoundEvents.BLOCK_ANVIL_USE, 1.0f, 1.0f);
		}
		player.getCooldownTracker().setCooldown(this, 20);
		player.removeExperienceLevel(25);
		setDamage(stack, getDamage(stack) - (getMaxDamage(stack) / 5));
	}
	
	//temp mana system
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		
		if (world.isRemote) {
			if (tick < 3 * 20) {
				tick++;
				return;
			}
			
			tick = 0;
			List<EntityPlayer> pl = world.playerEntities;
			
			for (EntityPlayer p : pl) {
				if (p.inventory.armorItemInSlot(2) != null && p.inventory.armorItemInSlot(2).getItem() == ModItems.DWARVEN_CHESTPLATE && this == ModItems.DWARVEN_CHESTPLATE) {
					if (p.experienceLevel < 1000) {
						p.addExperienceLevel(25);
					}
				} else if (p.inventory.armorItemInSlot(2) != null && p.inventory.armorItemInSlot(2).getItem() == ModItems.CRAFTER_CHESTPLATE && this == ModItems.CRAFTER_CHESTPLATE) {
					if (p.experienceLevel < 1000) {
						p.addExperienceLevel(25);
					}
				}
			}
		}
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
