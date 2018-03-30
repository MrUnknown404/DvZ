package com.mrunknown404.dvz.commands;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.util.EnumDwarfType;
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

	private final ITextComponent error1 = new TextComponentString("Invalid arguments");
	private final ITextComponent error2 = new TextComponentString("Player is already that role");
	private final ITextComponent error3 = new TextComponentString("Monsters have not been released so ");
	private final ITextComponent error4 = new TextComponentString(" has been forced to ");
	private final ITextComponent error5 = new TextComponentString("Cannot force player role game has not started");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 2) {
			sender.getCommandSenderEntity().sendMessage(error1);
			return;
		} else if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			sender.getCommandSenderEntity().sendMessage(error5);
			return;
		}
		
		//Start
		final ITextComponent txt = new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString());
		EntityPlayer player = (EntityPlayer) getEntity(server, sender, args[0]);
		IPlayerInfo cap = player.getCapability(PlayerInfoProvider.PLAYERINFO, null);
		
		if (args[1].equals(EnumPlayerType.spectator.name().toString()) && cap.getPlayerType() != EnumPlayerType.spectator) {
			GameManager.resetPlayer(player);
			sender.getCommandSenderEntity().sendMessage(txt);
		} else if (args[1].equals(EnumPlayerType.spectator.name().toString()) && cap.getPlayerType() == EnumPlayerType.spectator) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && cap.getPlayerType() != EnumPlayerType.dwarf) {
			GameManager.resetPlayer(player);
			if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
				if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
					GameManager.setupPlayerDwarf(player, EnumDwarfType.blacksmith);
				} else {
					GameManager.setupPlayerDwarf(player, EnumDwarfType.lumberjack);
				}
			} else {
				GameManager.setupPlayerDwarf(player, EnumDwarfType.builder);
			}
			sender.getCommandSenderEntity().sendMessage(txt);
		} else if (args[1].equals(EnumPlayerType.dwarf.name().toString()) && cap.getPlayerType() == EnumPlayerType.dwarf) {
			sender.getCommandSenderEntity().sendMessage(error2);
			return;
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && cap.getPlayerType() != EnumPlayerType.monster) {
			GameManager.resetPlayer(player);
			GameManager.giveSpawnAsMonsterItems(player);
			
			sender.getCommandSenderEntity().sendMessage(txt);
		} else if (args[1].equals(EnumPlayerType.monster.name().toString()) && cap.getPlayerType() == EnumPlayerType.monster) {
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
