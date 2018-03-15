package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.GameManager;
import com.mrunknown404.dvz.util.EnumMonsterType;
import com.mrunknown404.dvz.util.PlayerInfoProvider;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSpawnAsMob extends ItemBase {

	private EnumMonsterType enumMonsterType;
	
	public ItemSpawnAsMob(String name, CreativeTabs tab, String tooltip, EnumMonsterType type) {
		super(name, tab, tooltip);
		
		enumMonsterType = type;
		
		setMaxStackSize(1);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (entityLiving.getEntityWorld().isRemote) {
			return super.onEntitySwing(entityLiving, stack);
		}
		
		EntityPlayer player = (EntityPlayer) entityLiving;
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(enumMonsterType);
			GameManager.setupPlayerMonster(player, enumMonsterType);
		} else {
			player.sendMessage(new TextComponentString("Monsters have not been released"));
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getEntityWorld().isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		
		if (player.getEntityWorld().getScoreboard().getTeam("monsters") != null) {
			player.getCapability(PlayerInfoProvider.PLAYERINFO, null).setMonsterType(enumMonsterType);
			GameManager.setupPlayerMonster(player, enumMonsterType);
		} else {
			player.sendMessage(new TextComponentString("ÅòcMonsters have not been released"));
		}
		return super.onItemRightClick(world, player, hand);
	}
}
