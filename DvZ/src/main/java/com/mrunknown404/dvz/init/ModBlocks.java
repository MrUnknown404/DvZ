package com.mrunknown404.dvz.init;

import java.util.ArrayList;
import java.util.List;

import com.mrunknown404.dvz.blocks.ModBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//--> String name, Material material, SoundType soundType, CreativeTabs tab, float hardness, float resistance, String toolType, int harvestLevel
	public static final Block HARDDWARVENSTONE = new ModBlockBase("harddwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 2.5f, 100.0f, "pickaxe", 2);
	public static final Block CRACKEDHARDDWARVENSTONE = new ModBlockBase("crackedharddwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 2.0f, 100.0f, "pickaxe", 2);
	public static final Block DWARVENSTONE = new ModBlockBase("dwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 2.0f, 100.0f, "pickaxe", 2);
	public static final Block CRACKEDDWARVENSTONE = new ModBlockBase("crackeddwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 1.5f, 100.0f, "pickaxe", 2);
	public static final Block SOFTDWARVENSTONE = new ModBlockBase("softdwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 1.5f, 100.0f, "pickaxe", 2);
	public static final Block CRACKEDSOFTDWARVENSTONE = new ModBlockBase("crackedsoftdwarvenstone", Material.ROCK, SoundType.STONE, ModCreativeTabs.DVZBLOCKS, "", 1.0f, 100.0f, "pickaxe", 2);
}
