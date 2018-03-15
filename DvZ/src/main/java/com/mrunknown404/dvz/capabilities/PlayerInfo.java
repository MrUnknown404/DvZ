package com.mrunknown404.dvz.capabilities;

import com.mrunknown404.dvz.util.EnumDwarfType;
import com.mrunknown404.dvz.util.EnumHeroType;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.EnumPlayerType;

public class PlayerInfo implements IPlayerInfo {

	private EnumPlayerType enumPlayerType = EnumPlayerType.spec;
	private EnumDwarfType enumDwarfType = EnumDwarfType.nil;
	private EnumHeroType enumHeroType = EnumHeroType.nil;
	private EnumMonsterType enumMonsterType = EnumMonsterType.nil;
	
	@Override
	public void setPlayerType(EnumPlayerType type) {
		enumPlayerType = type;
	}
	
	@Override
	public void setDwarfType(EnumDwarfType type) {
		enumDwarfType = type;
	}
	
	@Override
	public void setHeroType(EnumHeroType type) {
		enumHeroType = type;
	}
	
	@Override
	public void setMonsterType(EnumMonsterType type) {
		
	}
	
	@Override
	public EnumPlayerType getPlayerType() {
		return enumPlayerType;
	}
	
	@Override
	public EnumDwarfType getDwarfType() {
		return enumDwarfType;
	}
	
	@Override
	public EnumHeroType getHeroType() {
		return enumHeroType;
	}

	@Override
	public EnumMonsterType getMonsterType() {
		return enumMonsterType;
	}
	
	@Override
	public void setPlayerTypeInt(int i) {
		enumPlayerType = EnumPlayerType.getNumber(i);
	}

	@Override
	public void setDwarfTypeInt(int i) {
		enumDwarfType = EnumDwarfType.getNumber(i);
	}
	
	@Override
	public void setHeroTypeInt(int i) {
		enumHeroType = EnumHeroType.getNumber(i);
	}
	
	@Override
	public void setMonsterTypeInt(int i) {
		enumMonsterType = EnumMonsterType.getNumber(i);
	}
}
