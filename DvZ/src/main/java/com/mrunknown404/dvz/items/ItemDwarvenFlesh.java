package com.mrunknown404.dvz.items;

import java.util.List;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemDwarvenFlesh extends ItemFood implements IHasModel {

	private String tooltip;
	
	public ItemDwarvenFlesh(String name, CreativeTabs tab, String tooltip) {
		super(20, 0, false);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setAlwaysEdible();
		
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
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		if (!world.isRemote) {
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 20, 3));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 20, 0));
			player.getCooldownTracker().setCooldown(this, 30 * 20);
		}
		stack.setCount(2);
		return super.onItemUseFinish(stack, world, entityLiving);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
