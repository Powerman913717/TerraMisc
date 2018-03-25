package terramisc.tileentities;

import terramisc.core.TFCMBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.bioxx.tfc.Core.TFC_Time;

public class TETallowCandle extends TileEntity 
{
	public int hourPlaced = -1000;
	public int color = 15;
	public ResourceLocation candleBase;

	public void create()
	{
		hourPlaced = (int) TFC_Time.getTotalHours();
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}
	
	public void setCandleTexture(int c)
	{
		color = c;
		
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		Block block = this.getWorldObj().getBlock(x, y, z);
		
		if(block == TFCMBlocks.blockTallowCandleOff)
		{
			switch(c)
			{
			case 0:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Black_Off.png");
				break;
			case 1:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Red_Off.png");
				break;
			case 2:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Green_Off.png");
				break;
			case 3:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Brown_Off.png");
				break;
			case 4:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Blue_Off.png");
				break;
			case 5:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Purple_Off.png");
				break;
			case 6:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Cyan_Off.png");
				break;
			case 7:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_LightGray_Off.png");
				break;
			case 8:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Gray_Off.png");
				break;
			case 9:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Pink_Off.png");
				break;
			case 10:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Lime_Off.png");
				break;
			case 11:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Yellow_Off.png");
				break;
			case 12:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_LightBlue_Off.png");
				break;
			case 13:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Magenta_Off.png");
				break;
			case 14:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Orange_Off.png");
				break;
			default:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Tallow_Off.png");
				break;
			}
		}
		else
		{
			switch(c)
			{
			case 0:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Black.png");
				break;
			case 1:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Red.png");
				break;
			case 2:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Green.png");
				break;
			case 3:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Brown.png");
				break;
			case 4:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Blue.png");
				break;
			case 5:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Purple.png");
				break;
			case 6:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Cyan.png");
				break;
			case 7:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_LightGray.png");
				break;
			case 8:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Gray.png");
				break;
			case 9:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Pink.png");
				break;
			case 10:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Lime.png");
				break;
			case 11:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Yellow.png");
				break;
			case 12:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_LightBlue.png");
				break;
			case 13:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Magenta.png");
				break;
			case 14:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Orange.png");
				break;
			default:
				candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Tallow.png");
				break;
			}
		}
		
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public ResourceLocation getCandleTexture()
	{
		if(candleBase != null && color == 15)
		{
			return candleBase;
		}
		else
		{
			this.setCandleTexture(color);
			return candleBase;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		hourPlaced = nbt.getInteger("hourPlaced");
		color = nbt.getInteger("color");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("hourPlaced", hourPlaced);
		nbt.setInteger("color", color);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) 
	{
		NBTTagCompound tagCom = pkt.func_148857_g();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket() 
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, tagCom);
	}
}
