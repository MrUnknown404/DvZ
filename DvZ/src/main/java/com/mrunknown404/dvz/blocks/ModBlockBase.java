package com.mrunknown404.dvz.blocks;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ModBlockBase extends Block implements IHasModel {
	
	/** String name, Material material, SoundType soundType, CreativeTabs tab, float hardness, float resistance, String toolType, int harvestLevel */
	public ModBlockBase(String name, Material material, SoundType soundType, CreativeTabs tab, float hardness, float resistance, String toolType, int harvestLevel) {
		super(material);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(toolType, harvestLevel);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
