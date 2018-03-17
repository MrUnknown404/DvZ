package com.mrunknown404.dvz.commands;

import java.util.List;

import com.mrunknown404.dvz.GameManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandResetGame extends CommandBase {

	@Override
	public String getName() {
		return "resetgame";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/resetgame (can be unstable!)";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcRESETING GAME");
	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 0) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
		
		if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			System.err.println(sender.getDisplayName().toString() + " has tried to use /resetgame when the game never started!");
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /resetgame when the game never started!"));
			return;
		}
		
		//Start
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		if (server.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			server.getEntityWorld().getScoreboard().removeTeam(server.getEntityWorld().getScoreboard().getTeam("monsters"));
		}
		
		server.getEntityWorld().getScoreboard().removeTeam(server.getEntityWorld().getScoreboard().getTeam("dwarves"));
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			GameManager.resetPlayer(player);
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "resetgame");
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
