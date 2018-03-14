package com.mrunknown404.dvz;

import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
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
		
		//event.world.getScoreboard().createTeam(name)
		
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
	
	@SubscribeEvent
	public void nameEvent(NameFormat event) {
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
			if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.blacksmith) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Blacksmith");
			} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.lumberjack) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Lumberjack");
			} else {
				event.setDisplayname("Åò3" + event.getUsername() + " the Dwarf");
			}
		} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			event.setDisplayname(event.getUsername());
		}
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.mrunknown404) {
			event.setDisplayname("Åò6" + event.getUsername() + " the Hero");
		}
	}
	
	public void updatePlayerMana(EntityPlayer p) {
		if (p.experienceLevel < 1000) {
			if (tick < 3 * 20) {
				tick++;
				return;
			}
			
			tick = 0;
			p.experienceLevel += 25;
			//p.addExperienceLevel(25); //this causes the level up sound so i'm not use it
			
			p.experience = 0;
			p.addExperience((int) (p.xpBarCap() * (p.experienceLevel * 0.001f)));
			
			if (p.experienceLevel == 1001) {
				p.experienceLevel = 1000;
				p.experience = 0;
				p.addExperience((int) (p.xpBarCap() - 1));
			}
		} else if (p.experienceLevel > 1000) {
			p.experienceLevel = 1000;
			updatePlayerMana(p);
		}
	}
	
	public static void setupPlayer(EntityPlayer player) {
		player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "dwarves");
		
		if (player.getName().equals("MrUnknown404")) {
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.mrunknown404);
		}
		
		if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
			if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
				player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.blacksmith);
			} else {
				player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.lumberjack);
			}
		} else {
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.builder);
		}
		
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.dwarf);
		
		player.experienceLevel = 1000;
		player.experience = 0;
		player.addExperience((int) (player.xpBarCap() - 1));
		
		player.clearActivePotions();
		player.inventory.clear();
		
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
	
	public static void resetPlayer(EntityPlayer player) {
		if (player.getEntityWorld().getScoreboard().getTeam("dwarves") != null) {
			player.getEntityWorld().getScoreboard().removePlayerFromTeam(player.getName().toString(), player.getEntityWorld().getScoreboard().getTeam("dwarves"));
		}
		
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.nil);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.spec);
		player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.nil);
		
		player.removeExperienceLevel(player.experienceLevel);
		player.experience = 0;
		player.addExperience(0);
		
		player.clearActivePotions();
		player.inventory.clear();
		player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, (60*60)*20, 5, true, false));
		
		player.refreshDisplayName();
	}
}
