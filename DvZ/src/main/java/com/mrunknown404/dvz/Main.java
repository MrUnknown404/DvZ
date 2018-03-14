package com.mrunknown404.dvz;

import com.mrunknown404.dvz.commands.CommandCreateQuarry;
import com.mrunknown404.dvz.commands.CommandDebugResetGame;
import com.mrunknown404.dvz.commands.CommandForcePlayerType;
import com.mrunknown404.dvz.commands.CommandStartGame;
import com.mrunknown404.dvz.proxy.CommonProxy;
import com.mrunknown404.dvz.util.Reference;
import com.mrunknown404.dvz.util.handlers.BlockHarvestHandler;
import com.mrunknown404.dvz.util.handlers.ConnectionHandler;
import com.mrunknown404.dvz.util.handlers.PlayerInfoCloneHandler;
import com.mrunknown404.dvz.util.handlers.WorldLoadHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerCapability();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GameManager());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CraftingManager.getInstance().getRecipeList().removeAll(CraftingManager.getInstance().getRecipeList());
		
		MinecraftForge.EVENT_BUS.register(new BlockHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new ConnectionHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInfoCloneHandler());
		MinecraftForge.EVENT_BUS.register(new WorldLoadHandler());
		
		Items.CAKE.setMaxStackSize(64);
		Blocks.SAND.setResistance(10000);
		Blocks.DIRT.setResistance(10000);
		Blocks.GRASS.setResistance(10000);
		Blocks.STONE.setResistance(10000);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandStartGame());
		event.registerServerCommand(new CommandDebugResetGame());
		event.registerServerCommand(new CommandCreateQuarry());
		event.registerServerCommand(new CommandForcePlayerType());
	}
}
