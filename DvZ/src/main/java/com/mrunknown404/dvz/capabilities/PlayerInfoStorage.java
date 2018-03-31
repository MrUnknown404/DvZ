package com.mrunknown404.dvz.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerInfoStorage implements IStorage<IPlayerInfo> {

	@Override
	public NBTBase writeNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("playertype", instance.getPlayerType().ordinal());
		nbt.setInteger("dwarftype", instance.getDwarfType().ordinal());
		nbt.setInteger("herotype", instance.getHeroType().ordinal());
		nbt.setInteger("monstertype", instance.getMonsterType().ordinal());
		nbt.setInteger("dragontype", instance.getDragonType().ordinal());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, EnumFacing side, NBTBase nbt) {
		instance.setDwarfTypeInt(((NBTTagCompound) nbt).getInteger("dwarftype"));
		instance.setPlayerTypeInt(((NBTTagCompound) nbt).getInteger("playertype"));
		instance.setHeroTypeInt(((NBTTagCompound) nbt).getInteger("herotype"));
		instance.setMonsterTypeInt(((NBTTagCompound) nbt).getInteger("monstertype"));
		instance.setDragonTypeInt(((NBTTagCompound) nbt).getInteger("dragontype"));
	}
}
