package com.mrunknown404.dvz.commands;

import java.util.Collections;
import java.util.List;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.util.ColoredStringHelper;
import com.mrunknown404.dvz.util.EnumDeathEventType;
import com.mrunknown404.dvz.util.EnumDragonType;
import com.mrunknown404.dvz.util.EnumMonsterType;
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

public class CommandForceMonsterType extends CommandBase {

	@Override
	public String getName() {
		return "forcemonster";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/forcemonster <player> <monstertype> <if dragon type of dragon>" ;
	}

	private final ITextComponent error1 = new TextComponentString(ColoredStringHelper.setColors("&cInvalid arguments"));
	private final ITextComponent error2 = new TextComponentString(ColoredStringHelper.setColors("&cPlayer is already that role"));
	private final ITextComponent error3 = new TextComponentString(ColoredStringHelper.setColors("&cCannot force player role game has not started"));
	private final ITextComponent error4 = new TextComponentString(ColoredStringHelper.setColors("&cMonsters have not been released"));
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 2) {
			if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
				sender.getCommandSenderEntity().sendMessage(error3);
				return;
			} else if (server.getEntityWorld().getScoreboard().getTeam("monsters") == null) {
				sender.getCommandSenderEntity().sendMessage(error4);
				return;
			} else if (args[1].equals(EnumMonsterType.dragon.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error1);
				return;
			}
			
			//Start
			final ITextComponent txt = new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString());
			EntityPlayer player = (EntityPlayer) getEntity(server, sender, args[0]);
			IPlayerInfo cap = player.getCapability(PlayerInfoProvider.PLAYERINFO, null);
			
			if (args[1].equals(EnumMonsterType.zombie.name().toString()) && cap.getMonsterType() != EnumMonsterType.zombie) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.zombie, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.zombie.name().toString()) && cap.getMonsterType() == EnumMonsterType.zombie) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.skeleton.name().toString()) && cap.getMonsterType() != EnumMonsterType.skeleton) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.skeleton, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.skeleton.name().toString()) && cap.getMonsterType() == EnumMonsterType.skeleton) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.creeper.name().toString()) && cap.getMonsterType() != EnumMonsterType.creeper) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.creeper, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.creeper.name().toString()) && cap.getMonsterType() == EnumMonsterType.creeper) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.wolf.name().toString()) && cap.getMonsterType() != EnumMonsterType.wolf) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.wolf, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.wolf.name().toString()) && cap.getMonsterType() == EnumMonsterType.wolf) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.spiderling.name().toString()) && cap.getMonsterType() != EnumMonsterType.spiderling) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.spiderling, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.spiderling.name().toString()) && cap.getMonsterType() == EnumMonsterType.spiderling) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.spider.name().toString()) && cap.getMonsterType() != EnumMonsterType.spider) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.spider, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.spider.name().toString()) && cap.getMonsterType() == EnumMonsterType.spider) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else if (args[1].equals(EnumMonsterType.supercreeper.name().toString()) && cap.getMonsterType() != EnumMonsterType.supercreeper) {
				GameManager.setupPlayerMonster(player, EnumMonsterType.supercreeper, null);
				sender.getCommandSenderEntity().sendMessage(txt);
			} else if (args[1].equals(EnumMonsterType.supercreeper.name().toString()) && cap.getMonsterType() == EnumMonsterType.supercreeper) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			} else {
				sender.getCommandSenderEntity().sendMessage(error1);
				return;
			}
		} else if (args.length == 3) {
			if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
				sender.getCommandSenderEntity().sendMessage(error3);
				return;
			} else if (server.getEntityWorld().getScoreboard().getTeam("monsters") == null) {
				sender.getCommandSenderEntity().sendMessage(error4);
				return;
			} else if (!args[1].equals(EnumMonsterType.dragon.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error1);
				return;
			} else if (!args[2].equals(EnumDragonType.vlarunga.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error1);
				return;
			}
			
			//Start
			final ITextComponent txt = new TextComponentString(getEntity(server, sender, args[0]).getName() + " has been forced into a " + args[1].toString());
			EntityPlayer player = (EntityPlayer) getEntity(server, sender, args[0]);
			IPlayerInfo cap = player.getCapability(PlayerInfoProvider.PLAYERINFO, null);
			
			if (args[1].equals(EnumMonsterType.dragon.name().toString()) && cap.getMonsterType() != EnumMonsterType.dragon) {
				if (args[2].equals(EnumDragonType.vlarunga.name().toString())) {
					GameManager.setupPlayerMonster(player, EnumMonsterType.dragon, EnumDragonType.vlarunga);
					sender.getCommandSenderEntity().sendMessage(txt);
					return;
				} else {
					sender.getCommandSenderEntity().sendMessage(error1);
					return;
				}
			} else if (args[1].equals(EnumMonsterType.dragon.name().toString()) && cap.getMonsterType() == EnumMonsterType.dragon) {
				sender.getCommandSenderEntity().sendMessage(error2);
				return;
			}
		} else {
			sender.getCommandSenderEntity().sendMessage(error1);
			return;
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "forcemonster");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length > 1) {
			if (!args[1].equals(EnumDeathEventType.dragon.name().toString())) {
				return args.length == 2 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumMonsterType.class)) : args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
			}
		}
		return args.length == 3 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumDragonType.class)) : args.length == 2 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumMonsterType.class)) : args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
}
