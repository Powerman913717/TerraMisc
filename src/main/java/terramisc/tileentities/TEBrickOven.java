package terramisc.tileentities;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.TileEntities.NetworkTileEntity;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.TileEntities.TEFireEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import terramisc.TerraMisc;
import terramisc.blocks.devices.BlockBrickOven;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMItems;
import terramisc.handlers.network.TFCMCreateMealPacket;

public class TEBrickOven extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[12]; //# of Storage Slots, equal to slots in ContainerBrickOven.
	public int lastTab; //Tab in GUI, 0=Pie, 1=Casserole.
	
	private final float[] pieWeights = new float[]{2,3,2,2,1}; //Determines the size of the meal and the amt of food used.
	private final float[] casseroleWeights = new float[]{2,3,2,2,1};
	
	private TEFireEntity fire; //TE used as the basis for TEFirepit and TEForge.
	
	//Multiblock TODO
	public boolean isFlipped;
	
	public TEBrickOven()
	{
		isFlipped = false;
	}
	
	public void openGui(EntityPlayer player) //Opens GUI and coresponding tab.
	{
		if(lastTab == 0)
			player.openGui(TerraMisc.instance, 3, worldObj, xCoord, yCoord, zCoord);
		else if (lastTab == 1)
			player.openGui(TerraMisc.instance, 4, worldObj, xCoord, yCoord, zCoord);
	}
	
	public void actionSwitchTab(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("tab", (byte)tab);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}
	
	public void actionCreate(EntityPlayer player) //Method used to produce meals.
	{
		if(!worldObj.isRemote)
		{
			if(lastTab == 0)
				createPie(player);
			else if(lastTab == 1)
				createCasserole(player);
		}
		else
		{
			// Send a network packet to call this method on the Server side
			// and create a meal, also adds 1 to the players cooking skill.
			AbstractPacket pkt = new TFCMCreateMealPacket(0, this);
			broadcastPacketInRange(pkt);
		}
	}
	
	public boolean validateIngreds(ItemStack... is) //Makes sure ingrediants are valid for usage.
	{
		for(int i = 0; i < is.length; i++)
		{
			if(is[i] == null)
				return false;
			
			for(int j = 0; j < is.length; j++)
			{

				if(j != i && !compareIngred(is[i], is[j]))
					return false;
			}
		}
		return true;
	}
	
	private boolean compareIngred(ItemStack is1, ItemStack is2) //Prevents using duplicate ingrediants.
	{
		return !(is1 != null && is2 != null && is1.getItem() == is2.getItem());
	}

	private void combineTastes(NBTTagCompound nbt, float[] weights, ItemStack... isArray) //Combines flavor profiles.
	{
		int tasteSweet = 0;
		int tasteSour = 0;
		int tasteSalty = 0;
		int tasteBitter = 0;
		int tasteUmami = 0;

		for (int i = 0; i < isArray.length; i++)
		{
			float weightMult = 1f;//weights[i] / totalW * 2;
			if(isArray[i] != null)
			{
				tasteSweet += ((IFood)isArray[i].getItem()).getTasteSweet(isArray[i]) * weightMult;
				tasteSour += ((IFood)isArray[i].getItem()).getTasteSour(isArray[i]) * weightMult;
				tasteSalty += ((IFood)isArray[i].getItem()).getTasteSalty(isArray[i]) * weightMult;
				tasteBitter += ((IFood)isArray[i].getItem()).getTasteBitter(isArray[i]) * weightMult;
				tasteUmami += ((IFood)isArray[i].getItem()).getTasteSavory(isArray[i]) * weightMult;
			}
		}
		nbt.setInteger("tasteSweet", tasteSweet);
		nbt.setInteger("tasteSour", tasteSour);
		nbt.setInteger("tasteSalty", tasteSalty);
		nbt.setInteger("tasteBitter", tasteBitter);
		nbt.setInteger("tasteUmami", tasteUmami);
	}

	public void consumeFoodWeight(float[] weights, ItemStack... isArray) //Removes the food used from ItemStacks
	{
		for(int i = 0; i < isArray.length; i++)
		{
			ItemStack is = isArray[i];
			if(is != null)
			{
				float oldW = Food.getWeight(is);
				Food.setWeight(is, oldW - weights[i]);
				float newW = Food.getWeight(is);
				if (newW <= 0 || newW <= Food.getDecay(is))
					is.stackSize = 0;
			}
		}
	}
	
	private void createPie(EntityPlayer player) 
	{
		if(validatePie())
		{
			ItemStack is = new ItemStack(TFCMItems.pie, 1); // TODO Item used in meal.

			float w = 0;
			for(int i = 0; i < 5; i++)
			{
				//Skip blocked slot
				if(i == 1)
					i++;
				
				ItemStack f = getStackInSlot(i);
				if (f != null && Food.getWeight(f) >= pieWeights[i])
					w = w + pieWeights[i];
			}

			ItemFoodTFC.createTag(is, w);
			Food.setDecayRate(is, 2.0F);

			int[] foodGroups = new int[]{-1,-1,-1,-1,-1};
			if(getStackInSlot(0) != null) foodGroups[0] = ((IFood)(getStackInSlot(0).getItem())).getFoodID();
			if(getStackInSlot(2) != null) foodGroups[1] = ((IFood)(getStackInSlot(2).getItem())).getFoodID();
			if(getStackInSlot(3) != null) foodGroups[2] = ((IFood)(getStackInSlot(3).getItem())).getFoodID();
			if(getStackInSlot(4) != null) foodGroups[3] = ((IFood)(getStackInSlot(4).getItem())).getFoodID();
			if(getStackInSlot(5) != null) foodGroups[4] = ((IFood)(getStackInSlot(5).getItem())).getFoodID();

			Food.setFoodGroups(is, foodGroups);
			setPieIcon(is);

			combineTastes(is.getTagCompound(), pieWeights, getStackInSlot(0), getStackInSlot(2), getStackInSlot(3), getStackInSlot(4), getStackInSlot(5));

			Food.setMealSkill(is, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
			this.setInventorySlotContents(6, is);

			consumeFoodWeight(pieWeights, getStackInSlot(0), getStackInSlot(2), getStackInSlot(3), 
					getStackInSlot(4), getStackInSlot(5));
		}
	}

	public boolean validatePie()
	{
		if(lastTab == 0)
		{
			if(storage[0] == null || storage[6] != null)
				return false;

			if(!validateIngreds(storage[0],storage[2],storage[3]))
				return false;
			
			if(((IFood) storage[2].getItem()).getFoodGroup() != EnumFoodGroup.Fruit)
				return false;
				

			float weight = 0;
			for(int i = 0; i < 5; i++)
			{
				if(i == 1)
					i++;
				
				ItemStack f = getStackInSlot(i);
				if (f != null && f.getItem() instanceof IFood && (Food.getWeight(f) - Food.getDecay(f) >= pieWeights[i]))
				{
					weight += pieWeights[i];
				}
			}

			if(weight < 7F)
				return false;
		}
		
		return true;
	}
	
	private void setPieIcon(ItemStack is) //Determines the texture used based on ingredients.
	{
		if(getStackInSlot(2).getItem() == TFCItems.redApple || getStackInSlot(2).getItem() == TFCItems.greenApple)
			is.setItemDamage(1);
		else if(getStackInSlot(2).getItem() == TFCItems.blackberry)
			is.setItemDamage(2);
		else if(getStackInSlot(2).getItem() == TFCItems.blueberry)
			is.setItemDamage(3);
		else if(getStackInSlot(2).getItem() == TFCItems.cherry)
			is.setItemDamage(4);
		else if(getStackInSlot(2).getItem() == TFCItems.peach)
			is.setItemDamage(5);
		else if(getStackInSlot(2).getItem() == TFCItems.lemon)
			is.setItemDamage(6);
		else if(getStackInSlot(2).getItem() == TFCItems.pumpkinGuts)
			is.setItemDamage(7);
		else if(getStackInSlot(2).getItem() == TFCItems.raspberry)
			is.setItemDamage(8);
		else if(getStackInSlot(2).getItem() == TFCItems.strawberry)
			is.setItemDamage(9);
		else //Default
			is.setItemDamage(0);

	}
	
	private void createCasserole(EntityPlayer player) // TODO
	{
		if(validateCasserole())
		{
			
		}
	}

	public boolean validateCasserole() // TODO
	{
		if(lastTab == 1)
		{
			return false;
		}
		
		return true;
	}
	
	private void setCasseroleIcon(ItemStack is) //Determines the texture used based on ingrediants. TODO
	{
		
	}
	
	//Multiblock TODO
	public void swapFlipped()
	{
		if(isFlipped)isFlipped = false;
		else isFlipped = true;
		if(!worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean isStackValid(int i, int j, int k)
	{
		Block yNegBlock = worldObj.getBlock(i, j - 1, k);
		if(!yNegBlock.isAir(worldObj, i, j - 1, k) && !worldObj.canBlockSeeTheSky(i, j - 1, k))
		{
			return false;
		}
		return ((BlockBrickOven)TFCMBlocks.blockBrickOven).checkStack(worldObj, xCoord, j, zCoord, worldObj.getBlockMetadata(xCoord, yCoord, zCoord)&3);
	}
	
	//Visuals
	public ResourceLocation getOvenTexture() //Sets texture for TESRBrickOven
	{
		this.fire = (TEFireEntity) worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		
		if(this.fire != null && this.fire.fireTemp >= 1F)
		{
			return new ResourceLocation("tfcm:textures/blocks/models/BrickOven_on.png");
		}
		
		return new ResourceLocation("tfcm:textures/blocks/models/BrickOven_off.png");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	//Inventory and Container Methods
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
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
		nbt.setTag("Items", nbttaglist);
		
		nbt.setBoolean("isFlipped", isFlipped);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", storage.length);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		nbt.setTag("Items", nbttaglist);
		
		isFlipped = nbt.getBoolean("isFlipped");
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

	public void ejectItem(int index)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(storage[index]!= null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[index]);
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.05F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}
	
	//IInventory: Methods added by IInventory
	@Override
	public int getSizeInventory() 
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return storage[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(storage[i] != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				storage[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage[i] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		if(!TFC_Core.areItemsEqual(storage[i], itemstack))
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		storage[i] = itemstack;
	}

	@Override
	public String getInventoryName() 
	{
		return "gui.BrickOven.name";
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
	public boolean isUseableByPlayer(EntityPlayer e) 
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

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 12);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		
		isFlipped = nbt.getBoolean("isFlipped");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
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
		nbt.setTag("Items", nbttaglist);
		
		nbt.setBoolean("isFlipped", this.isFlipped);
	}
	
}
