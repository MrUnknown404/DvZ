package com.mrunknown404.dvz.util.handlers;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ConnectionHandler {

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		//event.player.getCapability(PlayerInfoHandler.PLAYERINFO, null).setPlayerType(0);
		event.player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, (60*60)*20, 5, true, false));
	}
}
