package terramisc.tileentities;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import terramisc.core.TFCMBlocks;

public class TEWetClay extends TileEntity 
{
	public long startTime;
    public float bakingTime;
    public boolean canBake;
    
    public TEWetClay()
    {
    	startTime = 0;
    	bakingTime = 0;
    	canBake = false;
    }
    
    @Override
    public void updateEntity()
    {
      if (this.worldObj.isRemote)
          return;
      
      if(canBake)
    	  bakingTime += TFC_Time.getTotalHours() - startTime;
      
      if(bakingTime >= 6F) //Baking Time, remember to change baking time in wailadata if edited here.
      {
          this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, TFCMBlocks.blockStainedClay, 0, 2);
          this.invalidate();
      }
      else
      {
    	  startTime = TFC_Time.getTotalHours();
    	  canBake = canBake();
      }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
      super.readFromNBT(tag);
      
      startTime = tag.getLong("StartTime");
      bakingTime = tag.getFloat("BakingTime");
      canBake = tag.getBoolean("CanBake");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
      super.writeToNBT(tag);
      
      tag.setLong("StartTime", startTime);
      tag.setFloat("BakingTime", bakingTime);
      tag.setBoolean("CanBake", canBake);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }
    
    private boolean canBake()
    {
        return !this.worldObj.isRaining()
                && this.checkForHeat();
    }
    
    private boolean checkForHeat()
    {
    	return     this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord) == Blocks.fire
    			|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord) == Blocks.fire
    			|| this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord+1) == Blocks.fire
    			|| this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord-1) == Blocks.fire
    			|| this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord+1) == Blocks.fire
    	   		|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord-1) == Blocks.fire
    			|| this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord-1) == Blocks.fire

    			|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord-1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord-1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord-1) == Blocks.fire
    	    			
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord-1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord+1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord-1) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord-1) == Blocks.fire
    	    	
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord) == Blocks.fire
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord) == Blocks.fire
    	    	
    	    	//Lava
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord) == TFCBlocks.lavaStationary
    	   		|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord) == TFCBlocks.lavaStationary
    			|| this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord-1) == TFCBlocks.lavaStationary
    	   		|| this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord+1) == TFCBlocks.lavaStationary
     	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord+1) == TFCBlocks.lavaStationary
     	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord, this.zCoord-1) == TFCBlocks.lavaStationary

    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord+1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord+1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	    			
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord+1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord-1, this.yCoord-1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord+1, this.yCoord-1, this.zCoord-1) == TFCBlocks.lavaStationary
    	    	    	
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord+1, this.zCoord) == TFCBlocks.lavaStationary
    	    	|| this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord) == TFCBlocks.lavaStationary;
    }
}
