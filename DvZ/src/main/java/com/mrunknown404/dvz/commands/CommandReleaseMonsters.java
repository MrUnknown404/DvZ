package com.mrunknown404.dvz.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumDeathEventType;
import com.mrunknown404.dvz.util.EnumDragonType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.GetEnumNames;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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
		return "/releasemonsters <event> <if dragon type of dragon>";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcMONSTERS HAVE BEEN RELEASED");
	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 1) {
			if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
				sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the game has not started"));
				return;
			} else if (server.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
				sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the monsters have already been released"));
				return;
			} else if (args[0].equals(EnumDeathEventType.dragon.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error);
				return;
			}
			//Start
			List<EntityPlayer> players = server.getEntityWorld().playerEntities;
			
			server.getEntityWorld().getScoreboard().createTeam("monsters");
			server.getEntityWorld().getScoreboard().getTeam("monsters").setAllowFriendlyFire(false);
			server.getEntityWorld().getScoreboard().getTeam("monsters").setNameTagVisibility(EnumVisible.HIDE_FOR_OTHER_TEAMS);
			server.getEntityWorld().getScoreboard().getTeam("monsters").setDeathMessageVisibility(EnumVisible.ALWAYS);
			
			if (args[0].equals(EnumDeathEventType.none.name().toString())) {
				for (EntityPlayer player : players) {
					for (int i = 0; i < 5; i++) {
						player.sendMessage(msg);
					}
					
					if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spectator) {
						player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);;
						player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
						
						GameManager.resetPlayer(player);
						GameManager.giveSpawnAsMonsterItems(player);
					}
				}
			} else if (args[0].equals(EnumDeathEventType.plague.name().toString())) {
				List<EntityPlayer> canBeKilledPlayers = new ArrayList<EntityPlayer>();
				List<EntityPlayer> plaguePlayers = new ArrayList<EntityPlayer>();
				
				for (EntityPlayer player : players) {
					for (int i = 0; i < 5; i++) {
						player.sendMessage(msg);
					}
					
					if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spectator) {
						player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);;
						player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
						
						GameManager.resetPlayer(player);
						GameManager.giveSpawnAsMonsterItems(player);
					} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.nil) {
						canBeKilledPlayers.add(player);
					}
				}
				
				while (plaguePlayers.size() < (canBeKilledPlayers.size() / 3)) {
					EntityPlayer _p = canBeKilledPlayers.get(new Random().nextInt(canBeKilledPlayers.size()));
					if (_p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
						plaguePlayers.add(_p);
					}
				}
				
				for (EntityPlayer player : plaguePlayers) {
					player.sendMessage(new TextComponentString("Åò4You have the Crafter Plague!"));
					player.addPotionEffect(new PotionEffect(MobEffects.WITHER, (120 * 60) * 20, 2));
				}	
			}
		} else if (args.length == 2) {
			if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
				sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the game has not started"));
				return;
			} else if (server.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
				sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the monsters have already been released"));
				return;
			} else if (!args[0].equals(EnumDeathEventType.dragon.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error);
				return;
			} else if (!args[1].equals(EnumDragonType.vlarunga.name().toString())) {
				sender.getCommandSenderEntity().sendMessage(error);
				return;
			} //do for all dragon types
			
			List<EntityPlayer> players = server.getEntityWorld().playerEntities;
			
			server.getEntityWorld().getScoreboard().createTeam("monsters");
			server.getEntityWorld().getScoreboard().getTeam("monsters").setAllowFriendlyFire(false);
			server.getEntityWorld().getScoreboard().getTeam("monsters").setNameTagVisibility(EnumVisible.HIDE_FOR_OTHER_TEAMS);
			server.getEntityWorld().getScoreboard().getTeam("monsters").setDeathMessageVisibility(EnumVisible.ALWAYS);
			
			if (args[0].equals(EnumDeathEventType.dragon.name().toString())) {
				if (args[1].equals(EnumDragonType.vlarunga.name().toString())) {
					for (EntityPlayer player : players) {
						for (int i = 0; i < 5; i++) {
							player.sendMessage(msg);
						}
						
						if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spectator) {
							player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.monster);;
							player.getEntityWorld().getScoreboard().addPlayerToTeam(player.getName(), "monsters");
							
							GameManager.resetPlayer(player);
							GameManager.giveSpawnAsMonsterItems(player);
						}
					}
					GameManager.resetPlayer(getCommandSenderAsPlayer(sender));
					GameManager.setupPlayerMonster(getCommandSenderAsPlayer(sender), EnumMonsterType.dragon, EnumDragonType.vlarunga);
				} else {
					sender.getCommandSenderEntity().sendMessage(error);
				}
			} else {
				sender.getCommandSenderEntity().sendMessage(error);
			}
		} else {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		}
		
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "releasemonsters");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (!args[0].equals(EnumDeathEventType.dragon.name().toString())) {
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumDeathEventType.class)) : Collections.<String>emptyList();
		} else {
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumDeathEventType.class)) : args.length == 2 ? getListOfStringsMatchingLastWord(args, GetEnumNames.getNames(EnumDragonType.class)) : Collections.<String>emptyList();
		}
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
