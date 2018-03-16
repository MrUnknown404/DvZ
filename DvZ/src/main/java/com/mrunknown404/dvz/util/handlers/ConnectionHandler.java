package com.mrunknown404.dvz.util.handlers;

import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ConnectionHandler {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (event.player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			if (event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
				return;
			}
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.spec);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.nil);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.nil);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(EnumMonsterType.nil);
			
			event.player.clearActivePotions();
			event.player.inventory.clear();
			event.player.refreshDisplayName();
			
			event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_ZOMBIE));
			if (ThreadLocalRandom.current().nextBoolean()) {
				event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_CREEPER));
			}
			if (ThreadLocalRandom.current().nextBoolean()) {
				event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_SKELETON));
			}
		} else if (event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			event.player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, (60*60)*20, 5, true, false));
		}
	}
}
