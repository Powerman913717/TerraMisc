package terramisc.blocks.devices;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemBlocks.ItemTorch;
import com.dunk.tfc.Items.Tools.ItemCustomBucketMilk;
import com.dunk.tfc.Items.Tools.ItemFirestarter;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;
import scala.util.Random;
import terramisc.TerraMisc;
import terramisc.tileentities.TEVat;

public class BlockVat extends BlockTerraContainer
{
	private IIcon textureOn;
	private IIcon textureOff;
	
	public BlockVat()
	{
		super(Material.iron);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f); //TODO Properly configure block bounds
	}
	
	/**
	 * Ignites the vat when right-clicked while a player is holding a firestarter, flint&steel, or torch.
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(TFCOptions.enableDebugMode)
			{
				TEVat te = (TEVat) world.getTileEntity(x, y, z);
				
				TerraFirmaCraft.LOG.info("TFCM:Can Process?: " + te.canProcess());
				TerraFirmaCraft.LOG.info("TFCM:Proccess Timer: " + te.tickTimer);
				TerraFirmaCraft.LOG.info("TFCM:Cooking Timer: " + te.cookTimer);
			}
			
			if(entityplayer.isSneaking())
			{
				return false;
			}
			
			ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
			if(equippedItem != null)
			{
				if((TEVat) world.getTileEntity(x, y, z) != null)
				{
					TEVat te = (TEVat) world.getTileEntity(x, y, z);
					
					Item item = entityplayer.getCurrentEquippedItem().getItem();
					if(item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel || item instanceof ItemTorch)
					{
						if(te.fireTemp < 210 && te.storage[4] != null)
						{
							te.fireTemp = 300;
							
							if(item instanceof ItemFlintAndSteel)
							{
								Random rand = new Random();
								world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							}
							
							if(item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel)
							{
								equippedItem.damageItem(1, entityplayer);
							}
							
							if(item instanceof ItemTorch)
								equippedItem.stackSize--;
							
							world.setBlockMetadataWithNotify(x, y, z, 1, 3);
							return true;
						}
					}
					
					if(handleInteraction(entityplayer, te))
						return true;
				}
			}

			if((TEVat) world.getTileEntity(x, y, z) != null)
				entityplayer.openGui(TerraMisc.instance, 5, world, x, y, z);
		}
		else if(world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}

		return true;
	}
	
	/*
	 * Borrowed from BlockBarrel.java
	 * Handles fluid container interactions.
	 */
	protected boolean handleInteraction(EntityPlayer player, TEVat te) 
	{
		if(!te.getWorldObj().isRemote) 
		{
			ItemStack equippedItem = player.getCurrentEquippedItem();
			if(equippedItem == null)
				return false;

			if((FluidContainerRegistry.isFilledContainer(equippedItem)
					|| equippedItem.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem)equippedItem.getItem()).getFluid(equippedItem) != null))
			{
				ItemStack tmp = equippedItem.copy();
				tmp.stackSize = 1;
				ItemStack is = te.addLiquid(tmp);

				//If we cannot add the liquid to the barrel, open the interface.
				if(ItemStack.areItemStacksEqual(equippedItem, is))
				{
					return false;
				}

				equippedItem.stackSize--;

				if(equippedItem.stackSize == 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

				if(equippedItem.stackSize == 0 && (is.getMaxStackSize() == 1 || ! player.inventory.hasItemStack(is))) // put buckets in the slot you used them from.
					player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
				else
				{
					if(!player.inventory.addItemStackToInventory(is))
						player.dropPlayerItemWithRandomChoice(is, false); // if the new item dosn't fit, drop it.
				}

				if(player.inventoryContainer != null)
				{
					//for some reason the players inventory won't update without this.
					player.inventoryContainer.detectAndSendChanges();
				}

				return true;
			}
			else if(FluidContainerRegistry.isEmptyContainer(equippedItem) || equippedItem.getItem() instanceof IFluidContainerItem)
			{
				ItemStack tmp = equippedItem.copy();
				tmp.stackSize = 1;
				ItemStack is = te.removeLiquid(tmp);

				//If we cannot remove the liquid from the barrel, open the interface.
				if(ItemStack.areItemStacksEqual(equippedItem, is))
				{
					return false;
				}

				if(is.getItem() == TFCItems.woodenBucketMilk)
				{
					ItemCustomBucketMilk.createTag(is, 20f);
				}

				equippedItem.stackSize--;

				if(equippedItem.stackSize == 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

				if(equippedItem.stackSize == 0 && (is.getMaxStackSize() == 1 || ! player.inventory.hasItemStack(is))) // put buckets in the slot you used them from.
					player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
				else
				{
					if(!player.inventory.addItemStackToInventory(is))
						player.dropPlayerItemWithRandomChoice(is, false); // if the new item dosn't fit, drop it.
				}

				if(player.inventoryContainer != null)
				{
					// for some reason the players inventory won't update without this.
					player.inventoryContainer.detectAndSendChanges();
				}

				return true;
			}
			
			//Removed ability to swap fluids from barrels or large vessels.
		}
		return false;
	}
	
	@Override
	public IIcon getIcon(int i, int j)
	{
		if(j > 0)
			return textureOn;
		return textureOff;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	/**
	 * Block must be on a solid surface or it will eject its contents and remove itself.
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!TFC_Core.isTopFaceSolid(world, x, y - 1, z))
		{
			((TEVat)world.getTileEntity(x, y, z)).ejectContents();
			world.setBlockToAir(x, y, z);
			//TODO drop vat item block.
			return;
		}
	}
	
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 1)
		{
			if (rand.nextInt(24) == 0)
				world.playSoundEffect(x, y, z, "fire.fire", 0.4F + (rand.nextFloat() / 2), 0.7F + rand.nextFloat());

			float f = x + 0.5F;
			float f1 = y + 0.1F + rand.nextFloat() * 6F / 16F;
			float f2 = z + 0.5F;
			//float f3 = 0.52F;
			float f4 = rand.nextFloat() * 0.6F;
			float f5 = rand.nextFloat() * -0.6F;
			float f6 = rand.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			
			if (((TEVat)world.getTileEntity(x, y, z)).fireTemp > 550)
			{
				world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
			
			//TODO add particles for when liquids are boiling
		}
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
			return 0;
		else if(meta == 1)
			return 10;
		else
			return 15;
	}

	//TODO Make sure vat is also dropped.
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}

	//public void onBlockRemoval(World world, int x, int y, int z) {Eject(world, x, y, z);}

	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TEFirepit)
		{
			TEFirepit te = (TEFirepit)world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEVat();
	}
	
	/**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) 
    {
		if (world.getBlockMetadata(x, y, z) >= 1 && !entity.isImmuneToFire() && entity instanceof EntityLivingBase)
		{
			// Two ticks of fire damage will deal 100 HP of damage.
			entity.setFire(2);
		}
    }
}
