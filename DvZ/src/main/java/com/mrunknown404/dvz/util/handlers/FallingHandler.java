package com.mrunknown404.dvz.util.handlers;

import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FallingHandler {
	@SubscribeEvent
	public void FallingEvent(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (event.getEntityLiving().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.monster) {
			event.setDamageMultiplier(0);
			}
		}
	}
}
