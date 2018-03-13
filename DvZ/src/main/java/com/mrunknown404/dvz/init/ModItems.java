package com.mrunknown404.dvz.init;

import java.util.ArrayList;
import java.util.List;

import com.mrunknown404.dvz.items.ItemArmorBase;
import com.mrunknown404.dvz.items.ItemAxeBase;
import com.mrunknown404.dvz.items.ItemCrafterBow;
import com.mrunknown404.dvz.items.ItemDebugMana;
import com.mrunknown404.dvz.items.ItemDwarvenLongBow;
import com.mrunknown404.dvz.items.ItemGlue;
import com.mrunknown404.dvz.items.ItemJuice;
import com.mrunknown404.dvz.items.ItemPickaxeBase;
import com.mrunknown404.dvz.items.ItemShovelBase;
import com.mrunknown404.dvz.items.ItemSwordBase;
import com.mrunknown404.dvz.util.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static ToolMaterial MAT_DWARVEN = EnumHelper.addToolMaterial("MAT_DWARVEN", 3, -1, 10.0f, 5.0f, 0);
	public static ToolMaterial MAT_CRAFTER_DWARVEN = EnumHelper.addToolMaterial("MAT_CRAFTER_DWARVEN", 3, -1, 7.0f, 4.0f, 0);
	
	public static ArmorMaterial ARMMAT_DWARVEN = EnumHelper.addArmorMaterial("ARMMAT_DWARVEN",  Reference.MODID + ":dwarven", 44, new int[] {4, 7, 9, 4}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0f);
	public static ArmorMaterial ARMMAT_CRAFTER = EnumHelper.addArmorMaterial("ARMMAT_CRAFTER",  Reference.MODID + ":crafter", 38, new int[] {4, 6, 8, 4}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//-//-//Items
	//-//Items
	public static final Item GLUE = new ItemGlue("glue", ModCreativeTabs.DVZITEMS, "��aRepairs walls", false);
	public static final Item SUPER_GLUE = new ItemGlue("superglue", ModCreativeTabs.DVZITEMS, "��aRepairs walls", true);
	public static final Item JUICE = new ItemJuice("juice", ModCreativeTabs.DVZITEMS, "��aHeals you to full ��8(��bCosts 75 mana��8)");
	
	//Debug Items //��0D��1E��2B��3U��4G-��5D��6E��7B��8U��9G-��aD��bE��cB��dU��eG��f!
	public static final Item DEBUG_MANA = new ItemDebugMana("debugmana", null, "��aDebug item! ��8(��bGives/Costs 250 mana��8)");
	
	//-//Tools
	//Weapons
	public static final Item DWARVEN_SWORD = new ItemSwordBase("dwarvensword", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item CRAFTER_SWORD = new ItemSwordBase("craftersword", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	
	//Tools
	public static final Item DWARVEN_SHOVEL = new ItemShovelBase("dwarvenshovel", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item DWARVEN_PICKAXE = new ItemPickaxeBase("dwarvenpickaxe", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item DWARVEN_AXE = new ItemAxeBase("dwarvenaxe", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN, 12f, -3.2f);
	
	public static final Item CRAFTER_SHOVEL = new ItemShovelBase("craftershovel", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_PICKAXE = new ItemPickaxeBase("crafterpickaxe", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_AXE = new ItemAxeBase("crafteraxe", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN, 10f, -3.2f);
	
	public static final Item DWARVEN_LONGBOW = new ItemDwarvenLongBow("dwarvenlongbow", ModCreativeTabs.DVZITEMS, "��aLeft click to create arrows ��8(��bCosts 25 mana��8)");
	public static final Item CRAFTER_BOW = new ItemCrafterBow("crafterbow", ModCreativeTabs.DVZITEMS, "��aLeft click to create arrows ��8(��bCosts 25 mana��8)");
	
	//-//Armor
	//Dwarven
	public static final Item DWARVEN_HELMET = new ItemArmorBase("dwarvenhelmet", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.HEAD);
	public static final Item DWARVEN_CHESTPLATE = new ItemArmorBase("dwarvenchestplate", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.CHEST);
	public static final Item DWARVEN_LEGGINGS = new ItemArmorBase("dwarvenleggings", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_DWARVEN, 2, EntityEquipmentSlot.LEGS);
	public static final Item DWARVEN_BOOTS = new ItemArmorBase("dwarvenboots", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.FEET);
	
	//Crafter
	public static final Item CRAFTER_HELMET = new ItemArmorBase("crafterhelmet", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.HEAD);
	public static final Item CRAFTER_CHESTPLATE = new ItemArmorBase("crafterchestplate", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.CHEST);
	public static final Item CRAFTER_LEGGINGS = new ItemArmorBase("crafterleggings", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_CRAFTER, 2, EntityEquipmentSlot.LEGS);
	public static final Item CRAFTER_BOOTS = new ItemArmorBase("crafterboots", ModCreativeTabs.DVZITEMS, "��aLeft click to repair armor ��8(��bCosts 25 mana��8)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.FEET);
}
