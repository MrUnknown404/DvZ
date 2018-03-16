package com.mrunknown404.dvz.util.handlers;

import java.util.Random;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHandler {
	
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
					event.getDrops().add(new ItemStack(ModBlocks.STONECAKE));
				}
			} else {
				event.getDrops().clear();
				return;
			}
		} else {
			event.getDrops().clear();
			return;
		}
	}
	
	@SubscribeEvent
	public void onBlockPlace(PlayerInteractEvent.RightClickBlock event) {
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
			if (event.getEntityPlayer().getHeldItemMainhand().getItem() == Items.AIR) {
				return;
			} else {
				event.setCanceled(true);
				event.setUseBlock(Result.DENY);
			}
		}
	}
}
