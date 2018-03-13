package com.mrunknown404.dvz.capabilities;

import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumPlayerType;

public interface IPlayerInfo {

	public void setPlayerType(EnumPlayerType type);
	public void setDwarfType(EnumDwarfType type);
	public void setHeroType(EnumHeroType type);
	
	public EnumPlayerType getPlayerType();
	public EnumDwarfType getDwarfType();
	public EnumHeroType getHeroType();
	
	public void setPlayerTypeInt(int i);
	public void setDwarfTypeInt(int i);
	public void setHeroTypeInt(int i);
}
