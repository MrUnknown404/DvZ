package com.mrunknown404.dvz.util;

public enum EnumMonsterType {
	nil         (0),
	zombie      (1),
	creeper     (2),
	skeleton    (3),
	wolf        (4),
	spiderling  (5),
	spider      (6),
	supercreeper(7),
	dragon      (8);
	
	public final int fId;

	private EnumMonsterType(int id) {
		this.fId = id;
	}

	public static EnumMonsterType getNumber(int id) {
		for (EnumMonsterType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
