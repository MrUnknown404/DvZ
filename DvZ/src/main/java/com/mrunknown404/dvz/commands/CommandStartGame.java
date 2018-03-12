package com.mrunknown404.dvz.commands;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.handlers.PlayerInfoHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandStartGame extends CommandBase {

	@Override
	public String getName() {
		return "startgame";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "Starts the game!";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcSTARTING GAME");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			if (player.getCapability(PlayerInfoHandler.PLAYERINFO, null).getPlayerType() == 0) {
				if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {//rand.nextInt(2) == 0) {
					if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
						player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setDwarfType(1);
					} else {
						player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setDwarfType(2);
					}
				} else {
					player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setDwarfType(0);
				}
				player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setPlayerType(1);
			}
			
			player.experienceLevel = 1000;
			player.experience = 0;
			player.addExperience((int) (player.xpBarCap() - 1));
			
			player.clearActivePotions();
			player.inventory.clear();
			player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 10*20, 5, true, false));
			
			//-//Give items
			if (player.getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType() == 0) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_LONGBOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 16));
				
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 64));
			} else if (player.getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType() == 1) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 32));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 8));
				
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 32));			
			} else if (player.getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType() == 2) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_PICKAXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.DWARVEN_SWORD));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_BOW));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.JUICE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_SHOVEL));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.CRAFTER_AXE));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.GLUE, 32));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.SUPER_GLUE, 8));
				
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.CRACKEDSOFTDWARVENSTONE, 32));			
			}
			
			System.out.println(player.getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType());
			player.refreshDisplayName();
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "startgame");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
