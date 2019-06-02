package terramisc.tileentities;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import terramisc.api.crafting.VatRecipe;

public class TEVat extends TEFireEntity implements IInventory
{
	public byte rotation;
	public int mode;
	
	public ItemStack[] storage;
	public FluidStack fluid;
	
	private boolean sealed;
	public int sealtime;
	public int unsealtime;
	private int processTimer;

	public static final int MODE_IN = 0;
	public static final int MODE_OUT = 1;
	public static final int INPUT_SLOT = 0;
	public VatRecipe recipe;
	//temporary field. No need to save
	public boolean shouldDropItem = true;
	
	/**
	 * Crafting TE for combining items and fluids at specific temperatures over a period of time.
	 */
	public TEVat()
	{
		fuelTimeLeft = 375;
		fuelBurnTemp =  613;
		fireTemp = 350;
		maxFireTempScale = 2000;
		storage = new ItemStack[11];
	}

	/**
	 * Root Method for controlling TE Behavior
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			// Create a list of all the items that are falling onto the firepit
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1.1, zCoord + 1));
			
			if (list != null && !list.isEmpty() && storage[0] == null) // Only go through the list if more fuel can fit.
			{
				// Iterate through the list and check for logs and peat
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem) iterator.next();
					ItemStack is = entity.getEntityItem();
					Item item = is.getItem();

					if(item == TFCItems.logs || item == Item.getItemFromBlock(TFCBlocks.peat))
					{
						for(int c = 0; c < is.stackSize; c++)
						{
							if(storage[0] == null) // Secondary check for empty input slot.
							{
								/**
								 * Place a copy of only one of the logs into the fuel slot, due to the stack limitation of the fuel slots.
								 * Do not change to storage[0] = is;
								 */
								setInventorySlotContents(0, new ItemStack(item, 1, is.getItemDamage()));
								is.stackSize--;
								handleFuelStack(); // Attempt to shift the fuel down so more fuel can be added within the same for loop.
							}
						}

						if (is.stackSize == 0)
							entity.setDead();
					}
				}
			}
			
			//push the input fuel down the stack
			handleFuelStack();
			
			if (fireTemp < 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else if (fireTemp >= 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			//If the fire is still burning and has fuel
			if(fuelTimeLeft > 0 && fireTemp >= 1)
			{
				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && storage[5] != null &&
						!TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord))
			{
				if(storage[5] != null)
				{
					EnumFuelMaterial m = TFC_Core.getFuelMaterial(storage[5]);
					fuelTasteProfile = m.ordinal();
					storage[5] = null;
					fuelTimeLeft = m.burnTimeMax;
					fuelBurnTemp = m.burnTempMax;
				}
			}

			//Calculate the fire temp
			float desiredTemp = handleTemp();

			handleTempFlux(desiredTemp);

			//Here we handle the bellows
			handleAirReduction();
			
			if(fuelTimeLeft <= 0)
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
			
			//We only want to bother ticking food once per 5 seconds to keep overhead low.
			processTimer++;
			if(processTimer > 100)
			{
				processItems();
				processTimer = 0;
			}
			
			//TODO Handle Recipe Processing
		}
	}
	
	/**
	 * TODO Finish development of this method
	 */
	public void processItems()
	{
		
	}
	
	@Override
	public int getSizeInventory() 
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return storage[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		if(storage[slot] != null)
		{
			if(storage[slot].stackSize <= amount)
			{
				ItemStack itemstack = storage[slot];
				storage[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = storage[slot].splitStack(amount);
			if(storage[slot].stackSize == 0)
				storage[slot] = null;
			return itemstack1;
		}
		else
			return null;
	}
	
	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.3F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				storage[i] = null;
			}
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		return null;
	}

	public void handleFuelStack()
	{
		if(storage[3] == null && storage[0] != null)
		{
			storage[3] = storage[0];
			storage[0] = null;
		}
		if(storage[4] == null && storage[3] != null)
		{
			storage[4] = storage[3];
			storage[3] = null;
		}
		if(storage[5] == null && storage[4] != null)
		{
			storage[5] = storage[4];
			storage[4] = null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack is) 
	{
		storage[slot] = is;
		if(is != null && is.stackSize > getInventoryStackLimit())
			is.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName() 
	{
		return "Vat";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
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
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) 
	{
		return false;
	}
	
	public int getFluidLevel()
	{
		if(fluid != null)
			return fluid.amount;
		return 0;
	}
	
	public FluidStack getFluidStack()
	{
		return this.fluid;
	}
	
	public int getMaxLiquid()
	{
		return 10000;
	}

	public boolean addLiquid(FluidStack inFS)
	{
		if (inFS != null)
		{
			//Prevent Liquids that are hotter than boiling water from being stored.
			if (inFS.getFluid() != null && inFS.getFluid().getTemperature(inFS) > Global.HOT_LIQUID_TEMP)
				return false;

			if (fluid == null)
			{
				fluid = inFS.copy();
				if (fluid.amount > this.getMaxLiquid())
				{
					fluid.amount = getMaxLiquid();
					inFS.amount = inFS.amount - this.getMaxLiquid();

				}
				else
					inFS.amount = 0;
			}
			else
			{
				//check if the barrel is full or if the fluid being added does not match the barrel liquid
				if (fluid.amount == getMaxLiquid() || !fluid.isFluidEqual(inFS))
					return false;

				int a = fluid.amount + inFS.amount - getMaxLiquid();
				fluid.amount = Math.min(fluid.amount + inFS.amount, getMaxLiquid());
				if (a > 0)
					inFS.amount = a;
				else
					inFS.amount = 0;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}

		return false;
	}

	public ItemStack addLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isFilledContainer(is))
		{
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
			if(addLiquid(fs))
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return FluidContainerRegistry.drainFluidContainer(is);
			}
		}
		else if(is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs != null && addLiquid(isfs))
			{
				((IFluidContainerItem) is.getItem()).drain(is, is.getMaxDamage(), true);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return is;
	}

	/**
	 * This attempts to remove a portion of the water in this container and put it into a valid Container Item
	 */
	public ItemStack removeLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isEmptyContainer(is))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(fluid, is);
			if(out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				fluid.amount -= fs.amount;
				is = null;
				if(fluid.amount == 0)
				{
					fluid = null;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return out;
			}
		}
		else if(fluid != null && is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs == null || fluid.isFluidEqual(isfs))
			{
				fluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, fluid, true);
				if(fluid.amount == 0)
					fluid = null;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return is;
	}

	/**
	 * This removes a specified amount of liquid from the container and updates the block.
	 */
	public void drainLiquid(int amount)
	{
		if(!getSealed() && this.getFluidStack() != null)
		{
			this.getFluidStack().amount -= amount;
			if(this.getFluidStack().amount <= 0)
				this.actionEmpty();
			else
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getLiquidScaled(int i)
	{
		if(fluid != null)
			return fluid.amount * i/getMaxLiquid();
		return 0;
	}
	
	public boolean actionSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", true);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = true;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public boolean actionUnSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", false);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = false;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}
	
	public void actionEmpty()
	{
		fluid = null;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("fluidID", (byte)-1);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}
	
	public boolean getSealed()
	{
		return sealed;
	}
	
	public void setSealed()
	{
		sealed = true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		fluid = FluidStack.loadFluidStackFromNBT(nbttagcompound.getCompoundTag("fluidNBT"));
		sealed = nbttagcompound.getBoolean("Sealed");
		sealtime = nbttagcompound.getInteger("SealTime");
		rotation = nbttagcompound.getByte("rotation");
		
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
		
		nbttagcompound.setBoolean("Sealed", sealed);
		nbttagcompound.setInteger("SealTime", sealtime);
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(fluid != null)
			fluid.writeToNBT(fluidNBT);
		nbttagcompound.setTag("fluidNBT", fluidNBT);
		nbttagcompound.setByte("rotation", rotation);
		
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
	
	public void updateGui()
	{
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		this.rotation = nbt.getByte("rotation");
		this.sealed = nbt.getBoolean("sealed");
		this.sealtime = nbt.getInteger("SealTime");
		if(nbt.getInteger("fluid") != -1)
		{
			if(fluid != null)
				fluid.amount = nbt.getInteger("fluidAmount");
			else
				fluid = new FluidStack(nbt.getInteger("fluid"), nbt.getInteger("fluidAmount"));
		}
		else
		{
			fluid = null;
		}
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("sealed", sealed);
		nbt.setInteger("SealTime", sealtime);
		nbt.setInteger("fluid", fluid != null ? fluid.getFluidID() : -1);
		nbt.setInteger("fluidAmount", fluid != null ? fluid.amount : 0);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(nbt.hasKey("fluidID"))
		{
			if(nbt.getByte("fluidID") == -1)
				fluid = null;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if(!worldObj.isRemote)
		{	
			if(nbt.hasKey("mode"))
			{
				mode = nbt.getByte("mode");
			}
			else if(nbt.hasKey("seal"))
			{
				sealed = nbt.getBoolean("seal");
				if(!sealed)
				{
					unsealtime = (int) TFC_Time.getTotalHours();
					sealtime = 0;
				}
				else
				{
					sealtime = (int) TFC_Time.getTotalHours();
					unsealtime = 0;
				}

				// Broadcast the seal time to update the client
				NBTTagCompound timeTag = new NBTTagCompound();
				timeTag.setInteger("SealTime", sealtime);
				this.broadcastPacketInRange(this.createDataPacket(timeTag));

				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		else
		{
			// Get the seal time for the client display
			if (nbt.hasKey("SealTime"))
				sealtime = nbt.getInteger("SealTime");
		}
	}
}
