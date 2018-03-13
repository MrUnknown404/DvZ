package com.mrunknown404.dvz.util.handlers;

import com.mrunknown404.dvz.util.PlayerInfoProvider;
import com.mrunknown404.dvz.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	
	public static final ResourceLocation PLAYERINFO = new ResourceLocation(Reference.MODID, "playerinfo");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof EntityPlayer)) {
			return;
		}
		event.addCapability(PLAYERINFO, new PlayerInfoProvider());
	}
}
