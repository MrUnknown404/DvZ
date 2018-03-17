package com.mrunknown404.dvz.util.handlers;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerHandler {
	
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

			event.player.inventory.clear();
			event.player.clearActivePotions();
			event.player.refreshDisplayName();
			
			GameManager.giveSpawnAsMonsterItems(event.player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		IPlayerInfo info = player.getCapability(PlayerInfoProvider.PLAYERINFO, null);
		IPlayerInfo oldInfo = event.getOriginal().getCapability(PlayerInfoProvider.PLAYERINFO, null);
		
		if (event.getOriginal().getLastAttacker() instanceof EntityPlayer) {
			event.getOriginal().getLastAttacker().sendMessage(new TextComponentString("Debug!"));
		}
		
		info.setPlayerType(EnumPlayerType.spec);
		info.setDwarfType(EnumDwarfType.nil);
		info.setHeroType(EnumHeroType.nil);
		info.setMonsterType(EnumMonsterType.nil);
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.inventory.clear();
		player.refreshDisplayName();
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			GameManager.giveSpawnAsMonsterItems(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof EntityPlayer) {
			if (((EntityPlayer) event.getSource().getEntity()).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
				((EntityPlayer) event.getSource().getEntity()).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 4 * 20, 99));
			}
		}
	}
	
	@SubscribeEvent
	public void fallingEvent(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (event.getEntityLiving().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
				event.setDamageMultiplier(0);
			}
		}
	}
	
	@SubscribeEvent()
	public void dropItem(ItemTossEvent event) {
		if (event.getPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.dwarf) {
			event.setCanceled(true);
			ItemStack item = event.getEntityItem().getEntityItem();
			
			event.getPlayer().inventory.addItemStackToInventory(item);
			event.getPlayer().inventoryContainer.detectAndSendChanges();
		}
	}
	
	@SubscribeEvent
	public void onXPPickup(PlayerPickupXpEvent event) {
		event.getOrb().onKillCommand();
		event.setCanceled(true);
	}
}
