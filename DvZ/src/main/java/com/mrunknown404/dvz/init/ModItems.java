package com.mrunknown404.dvz.init;

import java.util.ArrayList;
import java.util.List;

import com.mrunknown404.dvz.items.ItemAxeBase;
import com.mrunknown404.dvz.items.ItemGlue;
import com.mrunknown404.dvz.items.ItemPickaxeBase;
import com.mrunknown404.dvz.items.ItemShovelBase;
import com.mrunknown404.dvz.items.ItemSwordBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static ToolMaterial MAT_DWARVEN = EnumHelper.addToolMaterial("MAT_DWARVEN", 3, -1, 10.0f, 5.0f, 0);
	public static ToolMaterial MAT_CRAFTER_DWARVEN = EnumHelper.addToolMaterial("MAT_CRAFTER_DWARVEN", 3, -1, 8.0f, 4.0f, 0);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//-//-//Items
	//-//Items
	public static final Item GLUE = new ItemGlue("glue", ModCreativeTabs.TabDvZItems, false);
	public static final Item SUPER_GLUE = new ItemGlue("superglue", ModCreativeTabs.TabDvZItems, true);
	
	//-//Tools
	//Weapons
	public static final Item DWARVEN_SWORD = new ItemSwordBase("dwarvensword", ModCreativeTabs.TabDvZItems, MAT_DWARVEN);
	public static final Item CRAFTER_SWORD = new ItemSwordBase("craftersword", ModCreativeTabs.TabDvZItems, MAT_CRAFTER_DWARVEN);
	
	//Tools
	public static final Item DWARVEN_SHOVEL = new ItemShovelBase("dwarvenshovel", ModCreativeTabs.TabDvZItems, MAT_DWARVEN);
	public static final Item DWARVEN_PICKAXE = new ItemPickaxeBase("dwarvenpickaxe", ModCreativeTabs.TabDvZItems, MAT_DWARVEN);
	public static final Item DWARVEN_AXE = new ItemAxeBase("dwarvenaxe", ModCreativeTabs.TabDvZItems, MAT_DWARVEN, 12f, -3.2f);
	
	public static final Item CRAFTER_SHOVEL = new ItemShovelBase("craftershovel", ModCreativeTabs.TabDvZItems, MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_PICKAXE = new ItemPickaxeBase("crafterpickaxe", ModCreativeTabs.TabDvZItems, MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_AXE = new ItemAxeBase("crafteraxe", ModCreativeTabs.TabDvZItems, MAT_CRAFTER_DWARVEN, 10f, -3.2f);
}
