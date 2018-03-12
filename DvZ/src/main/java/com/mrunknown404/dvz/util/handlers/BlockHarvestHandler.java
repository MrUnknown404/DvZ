package com.mrunknown404.dvz.util.handlers;

import java.util.Random;

import com.mrunknown404.dvz.init.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHarvestHandler {
	
	private Random rand = new Random();
	
	@SubscribeEvent
	public void harvestEvent(HarvestDropsEvent event) {
		if (event.getHarvester() != null) {
			if (event.getState() == Blocks.GRAVEL.getDefaultState()) {
				event.getDrops().clear();
				event.getDrops().add(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 1 + rand.nextInt(2)));
				if (rand.nextInt(8) == 0) {
					event.getDrops().add(new ItemStack(Blocks.TORCH, 1 + rand.nextInt(2)));
				}
				if (rand.nextInt(16) == 0) {
					event.getDrops().add(new ItemStack(Items.CAKE));
				}
			}
		}
	}
}
