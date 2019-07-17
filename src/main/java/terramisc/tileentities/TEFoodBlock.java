package terramisc.tileentities;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.NetworkTileEntity;
import com.dunk.tfc.api.Food;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.items.food.ItemPlaceableFood;

public class TEFoodBlock extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[1];
	
	@Override
	public void updateEntity()
	{
		if(storage[0] == null || Food.getWeight(storage[0]) <= Food.getDecay(storage[0]))
			onDecay(worldObj, xCoord, yCoord, zCoord);
		
		float temp = TFC_Climate.getBioTemperature(worldObj, xCoord, zCoord);
		float decay = TFC_Core.getEnvironmentalDecay(temp);
		
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord, decay);
	}

	/**
	 * Called if Food cannot be found in block or if decay has taken over the food.
	 */
	public void onDecay(World worldOBj, int x, int y, int z)
	{
		worldObj.setBlock(x, y, z, Blocks.air);
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te != null)
			te.invalidate();
	}
	
	@Override
	public int getSizeInventory() 
	{
		return 1;
	}

	/**
	 * Only returns storage, this inventory only has one slot.
	 */
	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return storage[slot];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(storage != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack is = storage[i];
				storage = null;
				return is;
			}
			ItemStack isSplit = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage = null;
			return isSplit;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		return storage[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack is) 
	{
		storage[slot] = is;
	}

	@Override
	public String getInventoryName() 
	{
		return "FoodBlock";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	/**
	 * Food items only require a stack size of one.
	 */
	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory() 
	{
		if(worldObj.isRemote)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	/**
	 * Checks to see if item is a valid food item.
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) 
	{
		if(is.getItem() instanceof ItemPlaceableFood)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}
}
