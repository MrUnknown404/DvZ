package com.mrunknown404.dvz.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandStartGame extends CommandBase {

	@Override
	public String getName() {
		return "startgame";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "Starts the game!";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		for (EntityPlayer player : players) {
			//dwarves.add(player);
			//player.capabilities.writeCapabilitiesToNBT();
			System.out.println(player);
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
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
