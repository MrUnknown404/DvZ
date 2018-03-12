package com.mrunknown404.dvz.commands;

import java.util.List;

import com.mrunknown404.dvz.util.handlers.PlayerInfoHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandDebugResetGame extends CommandBase {

	@Override
	public String getName() {
		return "resetgame";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "Resets the game!";
	}

	private final ITextComponent msg = new TextComponentString("ÅòcRESETING GAME");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setDwarfType(0);
			player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setPlayerType(0);
			
			player.experienceLevel = 0;
			player.experience = 0;
			player.addExperience(0);
			
			player.clearActivePotions();
			player.inventory.clear();
			player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, (60*60)*20, 5, true, false));
			
			player.refreshDisplayName();
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
