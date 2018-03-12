package com.mrunknown404.dvz.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerInfoStorage implements IStorage<IPlayerInfo> {

	@Override
	public NBTBase writeNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, EnumFacing side) {
		return new NBTTagInt(instance.getPlayerType());
	}

	@Override
	public void readNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, EnumFacing side, NBTBase nbt) {
		instance.setPlayerType(((NBTPrimitive) nbt).getInt());
	}

}
