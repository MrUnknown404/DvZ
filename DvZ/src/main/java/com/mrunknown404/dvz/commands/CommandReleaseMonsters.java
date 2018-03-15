package com.mrunknown404.dvz.commands;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team.EnumVisible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandReleaseMonsters extends CommandBase {

	@Override
	public String getName() {
		return "releasemonsters";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/releasemonsters";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcMONSTERS HAVE BEEN RELEASED");
	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 0) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
		
		if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			System.err.println(sender.getDisplayName().toString() + " has tried to use /releasemonster while the game has not started");
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the game has not started"));
			return;
		}
		
		if (server.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			System.err.println(sender.getDisplayName().toString() + " has tried to use /releasemonster while the monsters have already been released");
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the monsters have already been released"));
			return;
		}
		
		//Start
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		server.getEntityWorld().getScoreboard().createTeam("monsters");
		server.getEntityWorld().getScoreboard().getTeam("monsters").setAllowFriendlyFire(false);
		server.getEntityWorld().getScoreboard().getTeam("monsters").setNameTagVisibility(EnumVisible.HIDE_FOR_OTHER_TEAMS);
		server.getEntityWorld().getScoreboard().getTeam("monsters").setDeathMessageVisibility(EnumVisible.ALWAYS);
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
				player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);;
				player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
				
				player.inventory.clear();
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

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "releasemonsters");
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
