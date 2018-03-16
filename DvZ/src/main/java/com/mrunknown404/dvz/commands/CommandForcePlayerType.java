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

	private final ITextComponent error1 = new TextComponentString("ÅòcInvalid arguments");
	private final ITextComponent error2 = new TextComponentString("ÅòcPlayer is already that role");
	private final ITextComponent error3 = new TextComponentString("ÅòcMonsters have not been released so ");
	private final ITextComponent error4 = new TextComponentString(" Åòchas been forced to ");
	private final ITextComponent error5 = new TextComponentString("ÅòcCannot force player role game has not started");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 2) {
			sender.getCommandSenderEntity().sendMessage(error1);
			return;
		}
		
		if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			sender.getCommandSenderEntity().sendMessage(error5);
			return;
		}
		
		//Start
		if (args[1].equals(EnumPlayerType.spec.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.spec) {
			GameManager.resetPlayer((EntityPlayer) getEntity(server, sender, args[0]));
			sender.getCommandSenderEntity().sendMessage(new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString()));
		} else if (args[1].equals(EnumPlayerType.spec.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.dwarf) {
			GameManager.setupPlayerDwarf((EntityPlayer) getEntity(server, sender, args[0]));
			sender.getCommandSenderEntity().sendMessage(new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString()));
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() != EnumPlayerType.monster) {
			if (server.getEntityWorld().getScoreboard().getTeam("monsters") == null) {
				GameManager.resetPlayer((EntityPlayer) getEntity(server, sender, args[0]));
				final TextComponentString p = new TextComponentString(args[0].toString());
				final TextComponentString t = new TextComponentString(error3.toString() + p.toString() + error4.toString() + args[1].toString() + " (samething)");
				sender.getCommandSenderEntity().sendMessage(t);
				return;
			}
			
			EntityPlayer player = (EntityPlayer) getEntity(server, sender, args[0]);
			GameManager.giveEggs(player);
			
			sender.getCommandSenderEntity().sendMessage(new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString()));
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && getEntity(server, sender, args[0]).getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else {
			sender.getCommandSenderEntity().sendMessage(error1);
			return;
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "forceplayer");
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
