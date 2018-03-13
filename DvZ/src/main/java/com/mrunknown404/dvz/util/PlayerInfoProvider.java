package com.mrunknown404.dvz.util;

import com.mrunknown404.dvz.capabilities.IPlayerInfo;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerInfoProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IPlayerInfo.class)
	public static final Capability<IPlayerInfo> PLAYERINFO = null;
	
	private IPlayerInfo instance = PLAYERINFO.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == PLAYERINFO;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == PLAYERINFO ? PLAYERINFO.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return (NBTBase) PLAYERINFO.getStorage().writeNBT(PLAYERINFO, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		PLAYERINFO.getStorage().readNBT(PLAYERINFO, this.instance, null, nbt);
	}
}
