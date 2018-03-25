package terramisc.handlers;

import terramisc.core.TFCMRecipes;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TFCMChunkEventHandler 
{
    @SubscribeEvent 
    public void onLoadWorld(WorldEvent.Load e) 
    { 
        if (!e.world.isRemote && e.world.provider.dimensionId == 0) 
        { 
            TFCMRecipes.initialiseAnvil(); 
        } 
    } 
    
	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload e)
	{
		
	}
}
