package com.mrunknown404.dvz.util.handlers;

import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerCloneHandler {
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		IPlayerInfo info = player.getCapability(PlayerInfoProvider.PLAYERINFO, null);
		IPlayerInfo oldInfo = event.getOriginal().getCapability(PlayerInfoProvider.PLAYERINFO, null);
		
		info.setPlayerType(EnumPlayerType.spec);
		info.setDwarfType(EnumDwarfType.nil);
		info.setHeroType(EnumHeroType.nil);
		info.setMonsterType(EnumMonsterType.nil);
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.inventory.clear();
		player.refreshDisplayName();
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_ZOMBIE));
			if (ThreadLocalRandom.current().nextBoolean()) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_CREEPER));
			}
			if (ThreadLocalRandom.current().nextBoolean()) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_SKELETON));
			}
		}
	}
}
