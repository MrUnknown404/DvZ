package com.mrunknown404.dvz.util.handlers;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.spectator);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.nil);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.nil);
			event.player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(EnumMonsterType.nil);

			GameManager.playerCapabilities(event.player, false);
			
			event.player.inventory.clear();
			event.player.clearActivePotions();
			event.player.refreshDisplayName();
			
			GameManager.giveSpawnAsMonsterItems(event.player);
		}
	}
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) event.getEntityLiving();
			if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.dragon) {
				if (event.getAmount() <= 1) {
					event.setCanceled(true);
				}
			}
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
		
		info.setPlayerType(EnumPlayerType.spectator);
		info.setDwarfType(EnumDwarfType.nil);
		info.setHeroType(EnumHeroType.nil);
		info.setMonsterType(EnumMonsterType.nil);
		
		GameManager.playerCapabilities(player, false);
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.inventory.clear();
		player.refreshDisplayName();
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			GameManager.giveSpawnAsMonsterItems(player);
		}
	}
	
	@SubscribeEvent
	public void onDamage(LivingHurtEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.monster) {
				return;
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.zombie) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ZOMBIE_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ZOMBIE_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.creeper) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.skeleton) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_SKELETON_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.wolf) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_WOLF_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.spiderling) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_SPIDER_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_SPIDER_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.spider) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_SPIDER_DEATH, SoundCategory.HOSTILE, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_SPIDER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.supercreeper) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.HOSTILE, 1.0f, 1.0f);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.dragon) {
				if (player.getHealth() < event.getAmount()) {
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_DEATH, SoundCategory.WEATHER, 1.0f, 1.0f);
					return;
				}
				player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_HURT, SoundCategory.WEATHER, 1.0f, 1.0f);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof EntityPlayer) {
			if (((EntityPlayer) event.getSource().getEntity()).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
				if (((EntityPlayer) event.getSource().getEntity()).getHeldItemMainhand().getItem() == ModItems.DWARVEN_SWORD || ((EntityPlayer) event.getSource().getEntity()).getHeldItemMainhand().getItem() == ModItems.CRAFTER_SWORD) {
					((EntityPlayer) event.getSource().getEntity()).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 4 * 20, 99, false, false));
					((EntityPlayer) event.getSource().getEntity()).addPotionEffect(new PotionEffect(MobEffects.SPEED, 4 * 20, 0, false, false));
					((EntityPlayer) event.getSource().getEntity()).getEntityWorld().playSound(null, ((EntityPlayer) event.getSource().getEntity()).getPosition(), SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.PLAYERS, 0.4f, 2f);
					spawnParticles((EntityPlayer) event.getSource().getEntity());
				}
			}
		}
	}
	
	private void spawnParticles(EntityPlayer p) {
		((WorldServer)p.getEntityWorld()).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, p.posX, p.posY + 0.5D, p.posZ, 100, 1.0d, 1.0d, 1.0d, 0, new int[0]);
	}
	
	@SubscribeEvent
	public void fallingEvent(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (event.getEntityLiving().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster || event.getEntityLiving().getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.dragon) {
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
