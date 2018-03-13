package com.mrunknown404.dvz.util.handlers;

import java.util.Random;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHarvestHandler {
	
	@SubscribeEvent
	public void harvestEvent(HarvestDropsEvent event) {
		if (event.getHarvester() != null) {
			if (event.getState() == Blocks.GRAVEL.getDefaultState()) {
				event.getDrops().clear();
				event.getDrops().add(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 1 + new Random().nextInt(2)));
				if (new Random().nextInt(32) == 0) {
					event.getDrops().add(new ItemStack(Blocks.TORCH, 1 + new Random().nextInt(2)));
				}
				if (new Random().nextInt(64) == 0) {
					event.getDrops().add(new ItemStack(Items.CAKE));
				}
			}
		}
	}
}
