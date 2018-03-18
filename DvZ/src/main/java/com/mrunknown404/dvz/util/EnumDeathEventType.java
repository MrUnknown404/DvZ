package com.mrunknown404.dvz.util;

public enum EnumDeathEventType {
	none  (0),
	plague(1),
	dragon(2);
	
	public final int fId;

	private EnumDeathEventType(int id) {
		this.fId = id;
	}

	public static EnumDeathEventType getNumber(int id) {
		for (EnumDeathEventType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
