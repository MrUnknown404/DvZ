package com.mrunknown404.dvz.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {
	public static CreativeTabs DVZITEMS = (new CreativeTabs("TabDvZItems") {
		
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.DWARVEN_SWORD);
		};
		@Override
		public boolean hasSearchBar() {
			return true;
		};
	}).setBackgroundImageName("item_search.png");
	
	public static CreativeTabs DVZBLOCKS = (new CreativeTabs("TabDvZBlocks") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.HARDDWARVENSTONE);
		};
		@Override
		public boolean hasSearchBar() {
			return true;
		};
	}).setBackgroundImageName("item_search.png");
}
