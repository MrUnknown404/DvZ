package com.mrunknown404.dvz.util;

public enum EnumHeroType {
	nil         (0),
	mrunknown404(1);
	
	public final int fId;

	private EnumHeroType(int id) {
		this.fId = id;
	}

	public static EnumHeroType getNumber(int id) {
		for (EnumHeroType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
