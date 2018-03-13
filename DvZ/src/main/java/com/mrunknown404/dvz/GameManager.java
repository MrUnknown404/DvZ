package com.mrunknown404.dvz;

import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class GameManager {
	
	private int tick = 0;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		if (!event.world.isRemote) {
			for (EntityPlayer p : event.world.playerEntities) {
				if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
					updatePlayerMana(p);
					if (p.getEntityWorld().getLightFromNeighbors(p.getPosition()) <= 5 && !p.isPotionActive(MobEffects.BLINDNESS) && p.getHeldItemMainhand().getItem() != p.getHeldItemMainhand().getItem().getItemFromBlock(Blocks.TORCH) && p.getHeldItemOffhand().getItem() != p.getHeldItemOffhand().getItem().getItemFromBlock(Blocks.TORCH)) {
						p.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (60 * 60) * 20));
					} else if ((p.getEntityWorld().getLightFromNeighbors(p.getPosition()) > 5 && p.isPotionActive(MobEffects.BLINDNESS)) || ((p.getHeldItemMainhand().getItem() == p.getHeldItemMainhand().getItem().getItemFromBlock(Blocks.TORCH)) && p.isPotionActive(MobEffects.BLINDNESS)) || ((p.getHeldItemOffhand().getItem() == p.getHeldItemOffhand().getItem().getItemFromBlock(Blocks.TORCH)) && p.isPotionActive(MobEffects.BLINDNESS))) {
						p.clearActivePotions();
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void nameEvent(NameFormat event) {
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.dwarf) {
			if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.blacksmith) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Blacksmith");
			} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.lumberjack) {
				event.setDisplayname("Åò3" + event.getUsername() + " the Lumberjack");
			} else {
				event.setDisplayname("Åò3" + event.getUsername() + " the Dwarf");
			}
		} else if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getPlayerType() == EnumPlayerType.spec) {
			event.setDisplayname(event.getUsername());
		}
		if (event.getEntityPlayer().getCapability(PlayerInfoProvider.PLAYERINFO, null).getHeroType() == EnumHeroType.mrunknown404) {
			event.setDisplayname("Åò6" + event.getUsername() + " the Hero");
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
