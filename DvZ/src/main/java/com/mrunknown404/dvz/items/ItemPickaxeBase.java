package com.mrunknown404.dvz.items;

import java.util.List;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemPickaxeBase extends ItemPickaxe implements IHasModel {

	private String tooltip;
	
	/** String name, CreativeTabs tab, String tooltip, ToolMaterial material */
	public ItemPickaxeBase(String name, CreativeTabs tab, String tooltip, ToolMaterial material) {
		super(material);
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
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
