package com.mrunknown404.dvz.util;

public enum EnumDragonType {
	vlarunga(0); //will be more later
	
	public final int fId;

	private EnumDragonType(int id) {
		this.fId = id;
	}

	public static EnumDragonType getNumber(int id) {
		for (EnumDragonType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
