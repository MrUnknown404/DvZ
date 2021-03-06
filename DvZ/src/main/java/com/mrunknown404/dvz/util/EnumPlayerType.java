package com.mrunknown404.dvz.util;

public enum EnumPlayerType {
	spectator(0),
	dwarf    (1),
	monster  (2);
	
	public final int fId;

	private EnumPlayerType(int id) {
		this.fId = id;
	}

	public static EnumPlayerType getNumber(int id) {
		for (EnumPlayerType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
