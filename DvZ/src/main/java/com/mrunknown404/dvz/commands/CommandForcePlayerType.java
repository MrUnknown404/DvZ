package com.mrunknown404.dvz.commands;

import java.util.Collections;
import java.util.List;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.GetEnumNames;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

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
		return "/forceplayer <player> <playertype>" ;
	}

	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	private final ITextComponent error2 = new TextComponentString("ÅòcPlayer is already that role");
	
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
		if (args[1].equals(EnumPlayerType.spec.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.spec) {
			GameManager.resetPlayer((EntityPlayer) getEntity(server, sender, args[0]));
			return;
		} else if (args[1].equals(EnumPlayerType.spec.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.dwarf) {
			GameManager.setupPlayer((EntityPlayer) getEntity(server, sender, args[0]));
			return;
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.monster) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("this isn't setup"));
			return;
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
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
		return args.length == 2 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumPlayerType.class)) : args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

}
