package com.mrunknown404.dvz.commands;

import java.util.List;

import com.mrunknown404.dvz.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class CommandCreateQuarry extends CommandBase {

	@Override
	public String getName() {
		return "createquarry";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/createquarry";
	}
	
	private final ITextComponent error = new TextComponentString("Invalid arguments");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 0) {
			sender.getCommandSenderEntity().sendMessage(error);
			return;
		} else if (server.getEntityWorld().getTopSolidOrLiquidBlock(sender.getPosition()).getY() <= 63) {
			sender.getCommandSenderEntity().sendMessage(new TextComponentString("Too low (minimum Y:63)"));
			return;
		}
		
		sender.getCommandSenderEntity().sendMessage(new TextComponentString("Creating Quarry..."));
		
		WorldServer world = (WorldServer) server.getEntityWorld();
		TemplateManager tm = world.getStructureTemplateManager();
		Template t = tm.getTemplate(server, new ResourceLocation(Reference.MODID + ":quarry"));
		
		if (t == null) {
			System.err.println("Cannot find template ");
			return;
		}
		
		IBlockState iblockstate = server.getEntityWorld().getBlockState(sender.getPosition());
		server.getEntityWorld().notifyBlockUpdate(sender.getPosition(), iblockstate, iblockstate, 3);
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null)
				.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
		
		t.getDataBlocks(sender.getPosition(), placementsettings);
		t.addBlocksToWorld(server.getEntityWorld(), server.getEntityWorld().getTopSolidOrLiquidBlock(sender.getPosition()).add(-20, -64, -20), placementsettings);
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "createquarry");
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
