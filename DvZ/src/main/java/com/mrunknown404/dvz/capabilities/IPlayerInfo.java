package com.mrunknown404.dvz.capabilities;

import com.mrunknown404.dvz.util.EnumDragonType;
import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;

public interface IPlayerInfo {

	public void setPlayerType(EnumPlayerType type);
	public void setDwarfType(EnumDwarfType type);
	public void setHeroType(EnumHeroType type);
	public void setMonsterType(EnumMonsterType type);
	public void setDragonType(EnumDragonType type);
	
	public EnumPlayerType getPlayerType();
	public EnumDwarfType getDwarfType();
	public EnumHeroType getHeroType();
	public EnumMonsterType getMonsterType();
	public EnumDragonType getDragonType();
	
	public void setPlayerTypeInt(int i);
	public void setDwarfTypeInt(int i);
	public void setHeroTypeInt(int i);
	public void setMonsterTypeInt(int i);
	public void setDragonTypeInt(int i);
}
