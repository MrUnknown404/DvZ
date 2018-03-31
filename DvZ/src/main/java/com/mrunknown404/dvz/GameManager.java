package com.mrunknown404.dvz;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.ColoredStringHelper;
import com.mrunknown404.dvz.util.EnumDragonType;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;
import com.mrunknown404.dvz.util.handlers.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team.EnumVisible;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class GameManager {
	
	private int tick = 0;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		if (!event.world.isRemote) {
			for (EntityPlayer p : event.world.playerEntities) {
				if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
					updatePlayerMana(p);
					if (p.getEntityWorld().getLightFromNeighbors(p.getPosition()) <= 5 && !p.isPotionActive(MobEffects.BLINDNESS) && p.getHeldItemMainhand().getItem() != p.getHeldItemMainhand().getItem().getItemFromBlock(Blocks.TORCH) && p.getHeldItemOffhand().getItem() != p.getHeldItemOffhand().getItem().getItemFromBlock(Blocks.TORCH)) {
						p.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (60 * 60) * 20));
					} else if ((p.getEntityWorld().getLightFromNeighbors(p.getPosition()) > 5 && p.isPotionActive(MobEffects.BLINDNESS)) || ((p.getHeldItemMainhand().getItem() == p.getHeldItemMainhand().getItem().getItemFromBlock(Blocks.TORCH)) && p.isPotionActive(MobEffects.BLINDNESS)) || ((p.getHeldItemOffhand().getItem() == p.getHeldItemOffhand().getItem().getItemFromBlock(Blocks.TORCH)) && p.isPotionActive(MobEffects.BLINDNESS))) {
						p.clearActivePotions();
					}
				}
			}
		}
	}
	
	public void updatePlayerMana(EntityPlayer p) {
		if (p.experienceLevel < 1000) {
			if (tick < ConfigHandler.ManaRegenTime) {
				tick++;
				return;
			}
			
			tick = 0;
			p.experienceLevel += ConfigHandler.ManaRegenAmount;
			//p.addExperienceLevel(25); //this causes the level up sound so i'm not going to use it
			p.experience = 0;
			p.addExperience((int) (p.xpBarCap() * (p.experienceLevel * 0.001f)));
			
			if (p.experienceLevel > 1000) {
				p.experienceLevel = 1000;
				p.experience = 0;
				p.addExperience((int) (p.xpBarCap() - 1));
			}
		} else if (p.experienceLevel > 1000) {
			p.experienceLevel = 1000;
			updatePlayerMana(p);
		}
	}
	
	@SubscribeEvent
	public void nameEvent(NameFormat event) {
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
			if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.blacksmith) {
				event.setDisplayname(ColoredStringHelper.setColors("&3") + event.getUsername() + " the Blacksmith");
			} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.lumberjack) {
				event.setDisplayname(ColoredStringHelper.setColors("&3") + event.getUsername() + " the Lumberjack");
			} else {
				event.setDisplayname(ColoredStringHelper.setColors("&3") + event.getUsername() + " the Dwarf");
			}
		} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spectator) {
			event.setDisplayname(event.getUsername());
		}
		
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() == EnumMonsterType.dragon) {
			if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDragonType() == EnumDragonType.vlarunga) {
				event.setDisplayname(ColoredStringHelper.setColors("&4Vlarunga"));
			} else {
				event.setDisplayname("Invalid dragon");
			}
		} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
			event.setDisplayname(ColoredStringHelper.setColors("&c") + event.getUsername() + " the Monster");
		} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.mrunknown404) {
			event.setDisplayname(ColoredStringHelper.setColors("&6") + event.getUsername() + " the Creator");
		}
	}
	
	public static void resetPlayer(EntityPlayer player) {
		if (player.getEntityWorld().getScoreboard().getTeam("dwarves") != null) {
			if (player.getEntityWorld().getScoreboard().getPlayersTeam(player.getName()) == player.getEntityWorld().getScoreboard().getTeam("dwarves")) {
				player.getEntityWorld().getScoreboard().removePlayerFromTeam(player.getName().toString(), player.getEntityWorld().getScoreboard().getTeam("dwarves"));
			}
		} else if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			if (player.getEntityWorld().getScoreboard().getPlayersTeam(player.getName()) == player.getEntityWorld().getScoreboard().getTeam("monsters")) {
				player.getEntityWorld().getScoreboard().removePlayerFromTeam(player.getName().toString(), player.getEntityWorld().getScoreboard().getTeam("monsters"));
			}
		}
		
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.spectator);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.nil);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.nil);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(EnumMonsterType.nil);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDragonType(EnumDragonType.nil);
		
		playerCapabilities(player, false);
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.addExperience(0);
		
		player.clearActivePotions();
		player.inventory.clear();
		player.heal(player.getMaxHealth());
		
		player.refreshDisplayName();
	}
	
	public static void giveSpawnAsMonsterItems(EntityPlayer player) {
		player.refreshDisplayName();
		
		player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_ZOMBIE));
		if (ThreadLocalRandom.current().nextBoolean()) {
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_CREEPER));
		}
		if (ThreadLocalRandom.current().nextBoolean()) {
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_SKELETON));
		}
	}
	
	public static void giveSpawnAsDwarfItems(EntityPlayer player) {
		player.inventory.clear();
		player.clearActivePotions();
		player.refreshDisplayName();
		
		player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_BUILDER));
		if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
			if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_BLACKSMITH));
			} 
			if (ThreadLocalRandom.current().nextBoolean()) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SPAWNAS_LUMBERJACK));
			}
		}
	}
	
	public static void setupPlayerMonster(EntityPlayer player, EnumMonsterType type, @Nullable EnumDragonType dragonType) {
		resetPlayer(player);
		
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(type);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);
		
		if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getMonsterType() != EnumMonsterType.dragon) {
			player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
		}
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.addExperience(0);
		
		player.clearActivePotions();
		player.inventory.clear();
		player.getFoodStats().setFoodLevel(20);
		
		player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, (120 * 60) * 20, 0, false, false));
		switch (type) {
			case creeper: {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CREEPER_EXPLODE));
				
				player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 4)); //temporary
				break;
			}
			case dragon: {
				if (dragonType == null) {
					break;
				}
				
				player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDragonType(dragonType);
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DRAGON_TALONS));
				player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 5)); //temporary
				playerCapabilities(player, true);
				switch (dragonType) {
					case vlarunga: {
						player.inventory.addItemStackToInventory(new ItemStack(ModItems.DRAGON_FIREBREATH));
						player.inventory.addItemStackToInventory(new ItemStack(ModItems.DRAGON_FIREATTACK));
						
						player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, (120 * 60) * 20, 9, false, false));
						player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (120 * 60) * 20, 3, false, false));
					}
					default: {
						break;
					}
				}
				break;
			}
			case skeleton: {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SKELETON_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, ThreadLocalRandom.current().nextInt(6, 10)));
			
				player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 0)); //temporary
				player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
				
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, (120 * 60) * 20, 0, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (120 * 60) * 20, 0, false, false));
				break;
			}
			case spider: {
				break;
			}
			case spiderling: {
				break;
			}
			case supercreeper: {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CREEPER_EXPLODE));
				
				player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 4)); //temporary
				break;
			}
			case wolf: {
				break;
			}
			case zombie: {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ZOMBIE_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ZOMBIE_FLESH));
				
				player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 2)); //temporary
				player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
				
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (120 * 60) * 20, 0, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (120 * 60) * 20, 2, false, false));
				break;
			}
			default: {
				System.err.println(type.toString() + " is invalid!");
				break;
			}
		}
		player.refreshDisplayName();
		player.heal(player.getMaxHealth());
	}
	
	public static void playerCapabilities(EntityPlayer player, boolean allowFlight) {
		player.capabilities.allowFlying = allowFlight;
		//player.capabilities.setFlySpeed(0.1f); need to send packet will do alter
		player.sendPlayerAbilities();
	}
	
	public static void setupPlayerDwarf(EntityPlayer player, EnumDwarfType type) {
		player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "dwarves");
		
		if (player.getName().equals("MrUnknown404")) {
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.mrunknown404);
		}
		
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.dwarf);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(type);
		
		player.experienceLevel = 1000;
		player.experience = 0;
		player.addExperience((int) (player.xpBarCap() - 1));
		
		player.clearActivePotions();
		player.inventory.clear();
		player.heal(player.getMaxHealth());
		
		if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.mrunknown404) {
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.builder) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_LONGBOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.TORCH, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.CAKE, 64));
				
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.LADDER, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.blacksmith) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.TORCH, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.CAKE, 64));
				
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.LADDER, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));		
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.lumberjack) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.TORCH, 64));
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.CAKE, 64));
				
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.LADDER, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));			
			}
		} else {
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.builder) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_LONGBOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 16));
				
				player.inventory.addItemStackToInventory(new ItemStack(Blocks.LADDER, 16));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.blacksmith) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 16));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 4));
				
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 32));			
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.lumberjack) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 32));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 8));
				
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 32));			
			}
		}
		player.refreshDisplayName();
	}
	
	private static final ITextComponent MSG = new TextComponentString(ColoredStringHelper.setColors("&cMONSTERS HAVE BEEN RELEASED"));
	
	public static void releaseMonsters(EntityPlayer p) {
		if (p.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			return;
		}
		List<EntityPlayer> players = p.getEntityWorld().playerEntities;
		
		p.getEntityWorld().getScoreboard().createTeam("monsters");
		p.getEntityWorld().getScoreboard().getTeam("monsters").setAllowFriendlyFire(false);
		p.getEntityWorld().getScoreboard().getTeam("monsters").setNameTagVisibility(EnumVisible.HIDE_FOR_OTHER_TEAMS);
		p.getEntityWorld().getScoreboard().getTeam("monsters").setDeathMessageVisibility(EnumVisible.ALWAYS);
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(MSG);
			}
			
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spectator) {
				player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);;
				player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
				
				GameManager.resetPlayer(player);
				GameManager.giveSpawnAsMonsterItems(player);
			}
		}
	}
}
