package com.mrunknown404.dvz.client.gui;

import static com.mrunknown404.dvz.util.handlers.ConfigHandler.getConfiguration;

import java.util.ArrayList;
import java.util.List;

import com.mrunknown404.dvz.util.Reference;
import com.mrunknown404.dvz.util.handlers.ConfigHandler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ModGuiConfig extends GuiConfig {
	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen, getConfigElements(), Reference.MODID, false, true, GuiConfig.getAbridgedConfigPath(getConfiguration().toString()));
	}
	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(categoryElement(ConfigHandler.CATEGORY_GAMESETTINGS, "Game Settings","config.gamesettings"));
		list.add(categoryElement(ConfigHandler.CATEGORY_DWARVES, "Dwarves","config.dwarves"));
		list.add(categoryElement(ConfigHandler.CATEGORY_MONSTERS, "Monsters","config.monsters"));
		return list;
	}
	
	private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
		return new DummyConfigElement.DummyCategoryElement(name, tooltip_key, new ConfigElement(ConfigHandler.Config.getCategory(category)).getChildElements());
	}
}
