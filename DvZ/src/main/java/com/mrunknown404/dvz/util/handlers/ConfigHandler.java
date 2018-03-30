package com.mrunknown404.dvz.util.handlers;

import java.io.File;

import com.mrunknown404.dvz.util.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static Configuration Config;
	public static final String CONFIGNAME = "DwarvesVsZombies";
	
	public static int MaxDoomClockTime; //not implemented yet
	public static int TimeUntilMonstersRelease; //not implemented yet
	public static int DefaultDeathEventTypeInt; //not implemented yet
	//public static EnumDeathEventType DefaultDeathEventTypeEnum; //not implemented yet
	
	public static int ManaRegenAmount;
	public static int ManaRegenTime;
	public static int JuiceManaCost;
	public static int CraftArrowsManaCost;
	public static int RepairArmorManaCost;
	
	public static int SpawnAsMonsterTime;
	public static int CreeperExplodeTime;
	public static int SuperCreeperExplodeTime;
	
	public static final String CATEGORY_GAMESETTINGS = "gamesettings";
	public static final String CATEGORY_DWARVES = "dwarves";
	public static final String CATEGORY_MONSTERS = "monsters";
	
	public static void init(String ConfigDir) {
		if (Config == null) {
			File path = new File(ConfigDir + "/" + CONFIGNAME + ".cfg");
			Config = new Configuration(path);
			
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		MaxDoomClockTime = Config.getInt("Maximum Doom Clock Time", CATEGORY_GAMESETTINGS, 300, -1, 10000,"Maximum time for the Doom Clock (in seconds)");
		TimeUntilMonstersRelease = Config.getInt("Time Until Monsters Release", CATEGORY_GAMESETTINGS, 2400, 0, 10000,"Time until the monsters are released (in seconds)");
		DefaultDeathEventTypeInt = Config.getInt("Default Death Event Type", CATEGORY_GAMESETTINGS, 1, 0, 2, "The default death event type");
		//DefaultDeathEventTypeEnum = EnumDeathEventType.getNumber(DefaultDeathEventTypeInt);
		
		ManaRegenAmount = Config.getInt("Mana Regen Amount", CATEGORY_DWARVES, 25, 0, 1000, "Mana regen amount");
		ManaRegenTime = Config.getInt("Mana Regen Time", CATEGORY_DWARVES, 60, 0, 1000, "Amount of time needed to pass to give the players mana (in ticks)");
		JuiceManaCost = Config.getInt("Juice Mana Cost", CATEGORY_DWARVES, 100, 0, 1000, "Mana cost to use Juice");
		CraftArrowsManaCost = Config.getInt("Craft Arrows Mana Cost", CATEGORY_DWARVES, 25, 0, 1000, "Mana cost to craft arrows");
		RepairArmorManaCost = Config.getInt("Repair Armor Mana Cost", CATEGORY_DWARVES, 25, 0, 1000, "Mana cost to repair armor");
		
		SpawnAsMonsterTime = Config.getInt("Spawn As Monster Time", CATEGORY_MONSTERS, 10, 0, 100, "Amount of time to spawn as a monster (in ticks)");
		CreeperExplodeTime = Config.getInt("Creeper Explode Time", CATEGORY_MONSTERS, 40, 0, 100, "Amount of time needed to explode (in ticks)");
		SuperCreeperExplodeTime = Config.getInt("Super Creeper Explode Time", CATEGORY_MONSTERS, 80, 0, 100, "Amount of time needed to explode (in ticks)");
		
		if (Config.hasChanged()) {
			Config.save();
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Reference.MODID)) {
			loadConfiguration();
		}
	}
	
	public static Configuration getConfiguration() {
		return Config;
	}
}
