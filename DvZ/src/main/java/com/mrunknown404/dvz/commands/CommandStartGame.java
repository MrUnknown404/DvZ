package com.mrunknown404.dvz.commands;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
		return "/startgame";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcSTARTING GAME");
	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 0) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
		
		if (sender.getCommandSenderEntity().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.spec) {
			System.err.println(sender.getDisplayName().toString() + " has tried to use /startgame while the game has started");
			final ITextComponent msg2 = new TextComponentString("you have tried to use /startgame while the game has started");
			sender.getCommandSenderEntity().sendMessage(msg2);
			return;
		}
		
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
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
			}
			
			player.experienceLevel = 1000;
			player.experience = 0;
			player.addExperience((int) (player.xpBarCap() - 1));
			
			player.clearActivePotions();
			player.inventory.clear();
			
			//Give items
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
