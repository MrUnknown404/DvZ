package com.mrunknown404.dvz.util;

public enum EnumDwarfType {
	nil       (0),
	builder   (1),
	blacksmith(2),
	lumberjack(3);
	
	public final int fId;

	private EnumDwarfType(int id) {
		this.fId = id;
	}

	public static EnumDwarfType getNumber(int id) {
		for (EnumDwarfType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
