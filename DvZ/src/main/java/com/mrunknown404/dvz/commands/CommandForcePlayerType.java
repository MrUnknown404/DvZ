package com.mrunknown404.dvz.commands;

import java.util.Collections;
import java.util.List;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.GetEnumNames;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandForcePlayerType extends CommandBase {

	@Override
	public String getName() {
		return "forceplayer";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/forceplayer <playertype> <player>";
	}

	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 2) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
		
		if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			System.err.println("Cannot force player role game has not started");
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("Cannot force player role game has not started"));
			return;
		}
		
		//Start
		if (args[0].equals(EnumPlayerType.spec.name().toString())) {
			GameManager.resetPlayer((EntityPlayer) getEntity(server, sender, args[1]));
		} else if (args[0].equals(EnumPlayerType.dwarf.name().toString())) {
			GameManager.setupPlayer((EntityPlayer) getEntity(server, sender, args[1]));
		} else if (args[0].equals(EnumPlayerType.monster.name().toString())) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("this isn't setup"));
		} else {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "startgame");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumPlayerType.class)) : args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 1;
	}

}
