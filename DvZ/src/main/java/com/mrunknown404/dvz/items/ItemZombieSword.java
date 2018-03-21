package com.mrunknown404.dvz.items;

import java.util.List;
import java.util.Set;

import com.mrunknown404.dvz.Main;
import com.mrunknown404.dvz.init.ModBlocks;
import com.mrunknown404.dvz.init.ModItems;
import com.mrunknown404.dvz.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemZombieSword extends ItemTool implements IHasModel {

	private String tooltip;
	
	public ItemZombieSword(String name, CreativeTabs tab, String tooltip, ToolMaterial material, float attackDamage, float attackSpeed, Set<Block> effectiveBlocks) {
		super(attackDamage, attackSpeed, material, effectiveBlocks);
		setHarvestLevel("pickaxe", material.getHarvestLevel());
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setNoRepair();
		
		this.tooltip = tooltip;
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if (this.tooltip != "") {
			tooltip.add(this.tooltip);
		}
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if (state.getBlock() == ModBlocks.CRACKEDSOFTDWARVENSTONE) {
			return efficiencyOnProperMaterial;
		} else if (state.getBlock() == ModBlocks.SOFTDWARVENSTONE) {
			return efficiencyOnProperMaterial / 16;
		} else if (state.getBlock() == ModBlocks.CRACKEDDWARVENSTONE) {
			return efficiencyOnProperMaterial / 32;
		} else if (state.getBlock() == Blocks.WEB) {
			return 15.0f;
		}
		Material material = state.getMaterial();
		return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 0.25F : 1.5F;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		double yaw = Math.toRadians(player.rotationYaw);
		double pitch = Math.toRadians(player.rotationPitch);
		double xDirection = -Math.sin(yaw) * Math.cos(pitch);
		double yDirection = -Math.sin(pitch);
		double zDirection = Math.cos(yaw) * Math.cos(pitch);
		
		player.getCooldownTracker().setCooldown(this, 5 * 20);
		if (player.onGround) {
			player.addVelocity(xDirection * 5, yDirection * 5, zDirection * 5);
		} else {
			player.addVelocity(xDirection * 2, yDirection * 2, zDirection * 2);
		}
		
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
