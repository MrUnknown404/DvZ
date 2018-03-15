package com.mrunknown404.dvz.util.handlers;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldLoadHandler {

	@SubscribeEvent
	public void OnWorldLoad(WorldEvent.Load event) {
		event.getWorld().getGameRules().setOrCreateGameRule("spawnRadius", "0");
		event.getWorld().getGameRules().setOrCreateGameRule("doMobLoot", "false");
		event.getWorld().getGameRules().setOrCreateGameRule("doWeatherCycle", "false");
		event.getWorld().getGameRules().setOrCreateGameRule("doEntityDrops", "false");
		event.getWorld().getGameRules().setOrCreateGameRule("doFireTick", "false");
		event.getWorld().getGameRules().setOrCreateGameRule("doMobSpawning", "false");
		event.getWorld().getGameRules().setOrCreateGameRule("keepInventory", "true");
	}
}
