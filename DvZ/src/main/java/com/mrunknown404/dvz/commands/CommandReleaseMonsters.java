package com.mrunknown404.dvz.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;
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
		return "/releasemonsters";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcMONSTERS HAVE BEEN RELEASED");
	private final ITextComponent error = new TextComponentString("ÅòcInvalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 0) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		} else if (server.getEntityWorld().getScoreboard().getTeam("dwarves") == null) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the game has not started"));
			return;
		} else if (server.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("You have tried to use /releasemonster while the monsters have already been released"));
			return;
		}
		
		//Start
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		List<EntityPlayer> canBeKilledPlayers = new ArrayList<EntityPlayer>();
		List<EntityPlayer> plaguePlayers = new ArrayList<EntityPlayer>();
		
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
				
				GameManager.resetPlayer(player);
				GameManager.giveSpawnAsMonsterItems(player);
			} else if (player.getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.nil) {
				canBeKilledPlayers.add(player);
			}
		}
		
		while (plaguePlayers.size() < (canBeKilledPlayers.size() / 3)) {
			System.out.println(plaguePlayers + " : " + canBeKilledPlayers);
			EntityPlayer _p = canBeKilledPlayers.get(new Random().nextInt(canBeKilledPlayers.size()));
			System.out.println("0");
			if (_p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
				plaguePlayers.add(_p);
				System.out.println("1 : " + _p.getName().toString());
			}
			System.out.println("2");
		}
		
		for (EntityPlayer player : plaguePlayers) {
			System.out.println(player.getName() + " has been chosen");
			player.sendMessage(new TextComponentString("Åò4You have the Crafter Plague!"));
			player.addPotionEffect(new PotionEffect(MobEffects.WITHER, (120 * 60) * 20, 2));
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
