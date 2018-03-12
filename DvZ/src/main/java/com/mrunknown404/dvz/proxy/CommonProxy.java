package com.mrunknown404.dvz.proxy;

import com.mrunknown404.dvz.capabilities.IPlayerInfo;
import com.mrunknown404.dvz.capabilities.PlayerInfo;
import com.mrunknown404.dvz.capabilities.PlayerInfoStorage;
import com.mrunknown404.dvz.util.handlers.CapabilityHandler;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
	
	public void registerCapability() {
		CapabilityManager.INSTANCE.register(IPlayerInfo.class, new PlayerInfoStorage(), PlayerInfo.class);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());;
	}
}
