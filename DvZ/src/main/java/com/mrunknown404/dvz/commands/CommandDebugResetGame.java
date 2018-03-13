package com.mrunknown404.dvz.commands;

import java.util.List;

import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

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
		
		if (sender.getCommandSenderEntity().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			System.err.println(sender.getDisplayName().toString() + " has tried to use /resetgame when the game never started!");
			final ITextComponent msg2 = new TextComponentString("you have tried to use /resetgame when the game never started!");
			sender.getCommandSenderEntity().sendMessage(msg2);
			return;
		}
		
		List<EntityPlayer> players = server.getEntityWorld().playerEntities;
		
		for (EntityPlayer player : players) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(msg);
			}
			
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setDwarfType(EnumDwarfType.nil);
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setPlayerType(EnumPlayerType.spec);
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setHeroType(EnumHeroType.nil);
			
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
