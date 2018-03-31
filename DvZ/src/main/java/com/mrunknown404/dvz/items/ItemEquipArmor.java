package com.mrunknown404.dvz.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEquipArmor extends ItemBase {

	public ItemEquipArmor(String name, CreativeTabs tab, String tooltip, int maxStack) {
		super(name, tab, tooltip, maxStack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		/**
		 * NOT DONE!!
		 */
		
		/*
		Vec3d vec3d1 = player.getLook(1.0f);
		RayTraceResult r = world.rayTraceBlocks(player.getPositionEyes(1.0f), player.getPositionEyes(1.0f).addVector(vec3d1.xCoord * 20, vec3d1.yCoord * 20, vec3d1.zCoord * 20), false, false, true);
		
		if (r != null) {
			BlockPos pos = r.getBlockPos();
			System.out.println(pos);
			List<EntityLivingBase> b = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
			if (!b.isEmpty()) {
				System.out.println("not empty");
				for (EntityLivingBase e : b) {
					if (e != player) {
						if (e instanceof EntityPlayer) {
							EntityPlayer p = (EntityPlayer) e;
							if (p.getTotalArmorValue() != 0) {
									return super.onItemRightClick(world, player, hand);
								}
							if (p.getCapability(PlayerInfoProvider.PLAYERINFO, null).getDwarfType() == EnumDwarfType.builder) {
								p.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.DWARVEN_HELMET));
								p.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ModItems.DWARVEN_CHESTPLATE));
								p.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ModItems.DWARVEN_LEGGINGS));
								p.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ModItems.DWARVEN_BOOTS));
							} else {
								p.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.CRAFTER_HELMET));
								p.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ModItems.CRAFTER_CHESTPLATE));
								p.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ModItems.CRAFTER_LEGGINGS));
								p.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ModItems.CRAFTER_BOOTS));
							}
						}
					}
				}
			}
		}*/
		return super.onItemRightClick(world, player, hand);
	}
}
