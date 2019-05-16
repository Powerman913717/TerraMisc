package terramisc.handlers;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import scala.util.Random;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMRecipes;
import terramisc.worldGen.Generators.WorldGenGrowCropsTFCM;

public class ChunkEventHandlerTFCM //see ChunkEventHandler.class
{
    @SubscribeEvent
	public void onLoad(ChunkEvent.Load event)
	{
		if (!event.world.isRemote && TFC_Core.getCDM(event.world) != null && event.getChunk() != null)
		{
			ChunkData cd = TFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition);
			if (cd == null)
				return;
			//BiomeGenBase biome = event.world.getBiomeGenForCoords(event.getChunk().xPosition, event.getChunk().zPosition);
			int month = TFC_Time.getSeasonAdjustedMonth(event.getChunk().zPosition << 4);
			if (TFC_Time.getYear() > cd.lastSpringGen && month > 1 && month < 6)
			{
				int chunkX = event.getChunk().xPosition;
				int chunkZ = event.getChunk().zPosition;
				cd.lastSpringGen = TFC_Time.getYear();

				Random rand = new Random(event.world.getSeed() + ((chunkX >> 3) - (chunkZ >> 3)) * (chunkZ >> 3));
				String cropid = rand.nextString(CropManagerTFCM.getInstance().getTotalCrops());
				CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(cropid);
				if (event.world.rand.nextInt(25) == 0 && crop != null)
				{
					int num = 1 + event.world.rand.nextInt(5);
					WorldGenGrowCropsTFCM cropGen = new WorldGenGrowCropsTFCM();
					int x = (chunkX << 4) + event.world.rand.nextInt(16) + 8;
					int z = (chunkZ << 4) + event.world.rand.nextInt(16) + 8;
					cropGen.generate(event.world, event.world.rand, x, z, num, crop);
				}
			}
		}
	}
    
    @SubscribeEvent
	public void onUnload(ChunkEvent.Unload event)
	{

	}

	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload event)
	{
		
	}
	
	@SubscribeEvent 
    public void onLoadWorld(WorldEvent.Load e) 
    { 
        if (!e.world.isRemote && e.world.provider.dimensionId == 0) 
        { 
            TFCMRecipes.initialiseAnvil(); 
        } 
    } 
}
