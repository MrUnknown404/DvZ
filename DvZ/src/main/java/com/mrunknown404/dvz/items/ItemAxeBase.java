package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;

public class ItemAxeBase extends ItemAxe implements IHasModel {

	/** String name, CreativeTabs tab, ToolMaterial material, float damage, float speed */
	public ItemAxeBase(String name, CreativeTabs tab, ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}

