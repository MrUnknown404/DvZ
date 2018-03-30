package com.mrunknown404.dvz.init;

import java.util.ArrayList;
import java.util.List;

import com.mrunknown404.dvz.items.ItemArmorBase;
import com.mrunknown404.dvz.items.ItemAxeBase;
import com.mrunknown404.dvz.items.ItemCrafterBow;
import com.mrunknown404.dvz.items.ItemCreeperExplode;
import com.mrunknown404.dvz.items.ItemDebugMana;
import com.mrunknown404.dvz.items.ItemDragonAttack;
import com.mrunknown404.dvz.items.ItemDragonBreath;
import com.mrunknown404.dvz.items.ItemDragonTalons;
import com.mrunknown404.dvz.items.ItemDwarvenFlesh;
import com.mrunknown404.dvz.items.ItemDwarvenLongBow;
import com.mrunknown404.dvz.items.ItemEquipArmor;
import com.mrunknown404.dvz.items.ItemGlue;
import com.mrunknown404.dvz.items.ItemJuice;
import com.mrunknown404.dvz.items.ItemPickaxeBase;
import com.mrunknown404.dvz.items.ItemShovelBase;
import com.mrunknown404.dvz.items.ItemSkeletonBow;
import com.mrunknown404.dvz.items.ItemSpawnAsDwarf;
import com.mrunknown404.dvz.items.ItemSpawnAsMonster;
import com.mrunknown404.dvz.items.ItemSwordBase;
import com.mrunknown404.dvz.items.ItemZombieSword;
import com.mrunknown404.dvz.util.EnumDragonType;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumMonsterType;
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
	public static ToolMaterial MAT_ZOMBIE = EnumHelper.addToolMaterial("MAT_ZOMBIE", 0, -1, 10.0f, 6.0f, 0);
	
	public static ArmorMaterial ARMMAT_DWARVEN = EnumHelper.addArmorMaterial("ARMMAT_DWARVEN",  Reference.MODID + ":dwarven", 44, new int[] {4, 7, 9, 4}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0f);
	public static ArmorMaterial ARMMAT_CRAFTER = EnumHelper.addArmorMaterial("ARMMAT_CRAFTER",  Reference.MODID + ":crafter", 38, new int[] {4, 6, 8, 4}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item SPAWNAS_BUILDER = new ItemSpawnAsDwarf("spawnasbuilder", ModCreativeTabs.DVZITEMS, "Spawn as a Builder", 1, EnumDwarfType.builder, 5);
	public static final Item SPAWNAS_BLACKSMITH = new ItemSpawnAsDwarf("spawnasblacksmith", ModCreativeTabs.DVZITEMS, "Spawn as a Blacsmith", 1, EnumDwarfType.blacksmith, 5);
	public static final Item SPAWNAS_LUMBERJACK = new ItemSpawnAsDwarf("spawnaslumberjack", ModCreativeTabs.DVZITEMS, "Spawn as a Lumberjack", 1, EnumDwarfType.lumberjack, 5);
	
	public static final Item GLUE = new ItemGlue("glue", ModCreativeTabs.DVZITEMS, "Repairs walls", 64, false);
	public static final Item SUPER_GLUE = new ItemGlue("superglue", ModCreativeTabs.DVZITEMS, "Repairs walls", 64, true);
	public static final Item JUICE = new ItemJuice("juice", ModCreativeTabs.DVZITEMS, "Heals you to full (Costs 100 mana)",1);
	public static final Item EQUIP_ARMOR = new ItemEquipArmor("equiparmor", ModCreativeTabs.DVZITEMS, "Left/right click a dwarf to give him armor", 64);
	
	public static final Item DEBUG_MANA = new ItemDebugMana("debugmana", null, "Debug item! (Gives/Takes 250 mana)", 1);
	
	public static final Item DWARVEN_SWORD = new ItemSwordBase("dwarvensword", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item CRAFTER_SWORD = new ItemSwordBase("craftersword", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	
	public static final Item DWARVEN_SHOVEL = new ItemShovelBase("dwarvenshovel", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item DWARVEN_PICKAXE = new ItemPickaxeBase("dwarvenpickaxe", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN);
	public static final Item DWARVEN_AXE = new ItemAxeBase("dwarvenaxe", ModCreativeTabs.DVZITEMS, "", MAT_DWARVEN, 12f, -3.2f);
	
	public static final Item CRAFTER_SHOVEL = new ItemShovelBase("craftershovel", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_PICKAXE = new ItemPickaxeBase("crafterpickaxe", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN);
	public static final Item CRAFTER_AXE = new ItemAxeBase("crafteraxe", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN, 10f, -3.2f);
	
	public static final Item DWARVEN_LONGBOW = new ItemDwarvenLongBow("dwarvenlongbow", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", 1);
	public static final Item CRAFTER_BOW = new ItemCrafterBow("crafterbow", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", 1);
	
	public static final Item DWARVEN_HELMET = new ItemArmorBase("dwarvenhelmet", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.HEAD);
	public static final Item DWARVEN_CHESTPLATE = new ItemArmorBase("dwarvenchestplate", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.CHEST);
	public static final Item DWARVEN_LEGGINGS = new ItemArmorBase("dwarvenleggings", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_DWARVEN, 2, EntityEquipmentSlot.LEGS);
	public static final Item DWARVEN_BOOTS = new ItemArmorBase("dwarvenboots", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_DWARVEN, 1, EntityEquipmentSlot.FEET);
	
	public static final Item CRAFTER_HELMET = new ItemArmorBase("crafterhelmet", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.HEAD);
	public static final Item CRAFTER_CHESTPLATE = new ItemArmorBase("crafterchestplate", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.CHEST);
	public static final Item CRAFTER_LEGGINGS = new ItemArmorBase("crafterleggings", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_CRAFTER, 2, EntityEquipmentSlot.LEGS);
	public static final Item CRAFTER_BOOTS = new ItemArmorBase("crafterboots", ModCreativeTabs.DVZITEMS, "Left click to create arrows (Costs 25 mana)", ARMMAT_CRAFTER, 1, EntityEquipmentSlot.FEET);

	public static final Item SPAWNAS_ZOMBIE = new ItemSpawnAsMonster("spawnaszombie", ModCreativeTabs.DVZITEMS, "Spawn as a Zombie", 1, EnumMonsterType.zombie);
	public static final Item SPAWNAS_CREEPER = new ItemSpawnAsMonster("spawnascreeper", ModCreativeTabs.DVZITEMS, "Spawn as a Creeper", 1, EnumMonsterType.creeper);
	public static final Item SPAWNAS_SKELETON = new ItemSpawnAsMonster("spawnasskeleton", ModCreativeTabs.DVZITEMS, "Spawn as a Skeleton", 1, EnumMonsterType.skeleton);
	
	public static final Item ZOMBIE_SWORD = new ItemZombieSword("zombiesword", ModCreativeTabs.DVZITEMS, "", MAT_CRAFTER_DWARVEN, 0, 0, null);
	public static final Item ZOMBIE_FLESH = new ItemDwarvenFlesh("zombieflesh", ModCreativeTabs.DVZITEMS, "Restores hunger & gives buffs");
	
	public static final Item CREEPER_EXPLODE = new ItemCreeperExplode("creeperexplode", ModCreativeTabs.DVZITEMS, "Left/Right click to explode", 1);
	
	public static final Item SKELETON_BOW = new ItemSkeletonBow("skeletonbow", ModCreativeTabs.DVZITEMS, "Left click to create arrows", 1);

	public static final Item DRAGON_TALONS = new ItemDragonTalons("dragontalons", ModCreativeTabs.DVZITEMS, "Right click to pickup a player", 1);
	public static final Item DRAGON_FIREBREATH = new ItemDragonBreath("dragonfirebreath", ModCreativeTabs.DVZITEMS, "Left click to shoot fire", 1, EnumDragonType.vlarunga);
	public static final Item DRAGON_FIREATTACK = new ItemDragonAttack("dragonfireattack", ModCreativeTabs.DVZITEMS, "Left click to shoot fireballs", 1, EnumDragonType.vlarunga);
}
