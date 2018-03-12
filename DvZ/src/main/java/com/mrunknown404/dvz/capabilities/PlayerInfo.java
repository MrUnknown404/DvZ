package com.mrunknown404.dvz.capabilities;

public class PlayerInfo implements IPlayerInfo {

	private int playerType = 0;
	private int dwarfType = 0;

	@Override
	public void setPlayerType(int type) {
		playerType = type; //0 : spec, 1 : dwarf, 2 : monster
	}
	
	@Override
	public void setDwarfType(int type) {
		dwarfType = type; //0 : builder, 1 : crafter1, 2 : crafter2
	}
	
	@Override
	public int getPlayerType() {
		return playerType;
	}
	
	@Override
	public int getDwarfType() {
		return dwarfType;
	}

}
