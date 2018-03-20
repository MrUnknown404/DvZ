package com.mrunknown404.dvz.items;

import com.mrunknown404.dvz.util.EnumDragonType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDragonAttack extends ItemBase {

	private EntityLargeFireball ball;
	private EnumDragonType type;
	
	public ItemDragonAttack(String name, CreativeTabs tab, String tooltip, EnumDragonType type) {
		super(name, tab, tooltip);
		this.type = type;
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getEntityWorld().isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		if (type == EnumDragonType.vlarunga) {
			double yaw = Math.toRadians(player.rotationYaw);
			double pitch = Math.toRadians(player.rotationPitch);
			double xDirection = -Math.sin(yaw) * Math.cos(pitch);
			double yDirection = -Math.sin(pitch);
			double zDirection = Math.cos(yaw) * Math.cos(pitch);
			double x = player.posX + (xDirection * 1.5);
			double y = player.posY + 1.5 + (yDirection * 2);
			double z = player.posZ + (zDirection * 1.5);
			
			EntityLargeFireball ball = new EntityLargeFireball(world, x, y, z, xDirection, yDirection, zDirection);
			ball.explosionPower = 4;
			world.spawnEntity(ball);
			
			player.getCooldownTracker().setCooldown(this, 7);
		}
		return super.onItemRightClick(world, player, hand);
	}
}
