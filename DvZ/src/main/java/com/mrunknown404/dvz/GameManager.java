package com.mrunknown404.dvz;

import java.util.List;

import com.mrunknown404.dvz.util.handlers.PlayerInfoHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class GameManager {
	
	private int tick = 0;
	
	public List<EntityPlayer> dwarves;
	public List<EntityPlayer> players;

	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		if (!event.world.isRemote) {
			players = event.world.playerEntities;
			
			for (EntityPlayer p : players) {
				if (p.getCapability(PlayerInfoHandler.PLAYERINFO, null).getPlayerType() == 1) {
					updatePlayerMana(p);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void nameEvent(NameFormat event) {
		if (event.getEntityPlayer().getCapability(PlayerInfoHandler.PLAYERINFO, null).getPlayerType() == 1) {
			if (event.getEntityPlayer().getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType() == 1) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Crafter");
			} else if (event.getEntityPlayer().getCapability(PlayerInfoHandler.PLAYERINFO, null).getDwarfType() == 2) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Crafter");
			} else {
				event.setDisplayname("Åò3" + event.getUsername() + " the Dwarf");
			}
		} else if (event.getEntityPlayer().getCapability(PlayerInfoHandler.PLAYERINFO, null).getPlayerType() == 0) {
			event.setDisplayname("Åòf" + event.getUsername());
		}
	}
	
	public void updatePlayerMana(EntityPlayer p) {
		if (p.experienceLevel < 1000) {
			if (tick < 4 * 20) {
				tick++;
				return;
			}
			
			tick = 0;
			p.experienceLevel += 25;
			//p.addExperienceLevel(25); //this causes the level up sound so i'm not use it
			
			p.experience = 0;
			p.addExperience((int) (p.xpBarCap() * (p.experienceLevel * 0.001f)));
			
			if (p.experienceLevel == 1001) {
				p.experienceLevel = 1000;
				p.experience = 0;
				p.addExperience((int) (p.xpBarCap() - 1));
			}
		} else if (p.experienceLevel > 1000) {
			p.experienceLevel = 1000;
			updatePlayerMana(p);
		}
	}
}
