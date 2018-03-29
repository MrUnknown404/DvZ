package com.mrunknown404.dvz.commands;

import java.util.List;

import com.mrunknown404.dvz.GameManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team.EnumVisible;
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
		} else if (server.getEntityWorld().getScoreboard().getTeam("dwarves") != null) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /startgame while the game has started"));
			return;
		}
		
		//Start
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		server.getEntityWorld().getScoreboard().createTeam("dwarves");
		server.getEntityWorld().getScoreboard().getTeam("dwarves").setAllowFriendlyFire(false);
		server.getEntityWorld().getScoreboard().getTeam("dwarves").setNameTagVisibility(EnumVisible.HIDE_FOR_OTHER_TEAMS);
		server.getEntityWorld().getScoreboard().getTeam("dwarves").setDeathMessageVisibility(EnumVisible.ALWAYS);
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			GameManager.giveSpawnAsDwarfItems(player);
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
