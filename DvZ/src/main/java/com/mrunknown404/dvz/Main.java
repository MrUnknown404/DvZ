package com.mrunknown404.dvz;

import com.mrunknown404.dvz.commands.CommandDebugResetGame;
import com.mrunknown404.dvz.commands.CommandStartGame;
import com.mrunknown404.dvz.proxy.CommonProxy;
import com.mrunknown404.dvz.util.Reference;
import com.mrunknown404.dvz.util.handlers.BlockHarvestHandler;
import com.mrunknown404.dvz.util.handlers.ConnectionHandler;

import net.minecraft.init.Items;
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
		//CapabilityManager.INSTANCE.register(IPlayerType.class, new PlayerTypeStorage(), PlayerType.class);
		//MinecraftForge.EVENT_BUS.register(new CapabilityHandler());;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GameManager());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new BlockHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new ConnectionHandler());
		
		Items.CAKE.setMaxStackSize(64);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandStartGame());
		event.registerServerCommand(new CommandDebugResetGame());
	}
}
