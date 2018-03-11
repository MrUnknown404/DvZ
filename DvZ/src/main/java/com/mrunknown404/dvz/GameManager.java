package com.mrunknown404.dvz;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class GameManager {
	
	private int tick = 0;
	
	private List<EntityPlayer> dwarves;
	
	//for later use
	public void startGame(World world) {
		List<EntityPlayer> pl = world.playerEntities;
		
		for (EntityPlayer p : pl) {
			dwarves.add(p);
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		if (!event.world.isRemote) {
			List<EntityPlayer> pl = event.world.playerEntities;
			
			//NBTTagCompound tag = player.getEntityData();
			
			for (EntityPlayer p : pl) {
				updatePlayerMana(p);
			}
		}
	}
	
	public void updatePlayerMana(EntityPlayer p) {
		if (p.experienceLevel < 1000) {
			if (tick < 3 * 20) {
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
