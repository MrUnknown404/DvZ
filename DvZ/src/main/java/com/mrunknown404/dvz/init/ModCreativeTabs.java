package com.mrunknown404.dvz.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {
	public static CreativeTabs TabDvZItems = new CreativeTabs("TabDvZItems") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.DWARVEN_SWORD);
		}
	};
	
	public static CreativeTabs TabDvZBlocks = new CreativeTabs("TabDvZBlocks") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.DWARVENSTONE);
		}
	};
}
