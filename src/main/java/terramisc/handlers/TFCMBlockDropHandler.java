package terramisc.handlers;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import terramisc.core.TFCMItems;

public class TFCMBlockDropHandler 
{
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		Block block = event.block;
		World worldObj = event.world;
		EntityPlayer p = event.getPlayer();
		
		Random r = new Random();
		
		ItemStack pumpkin = ItemFoodTFC.createTag(new ItemStack(TFCMItems.pumpkin), CropIndex.getWeight(4F, r));
		ItemStack seed = new ItemStack(TFCMItems.seedsPumpkin);
		
		if(block == TFCBlocks.pumpkin)
		{
			event.world.setBlock(event.x, event.y, event.z, Blocks.air); //Deletes pumpkin to prevent duplication.
			
				//Drops pumpkin food item
			EntityItem entityitem = new EntityItem(worldObj, event.x + 0.5, event.y + 0.5, event.z + 0.5, pumpkin);
            entityitem.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(entityitem);
            
            //Controls chance to drop seeds.
            if (worldObj.rand.nextFloat() < 0.33F)
            {
            	//Extra seeds dropped based on skill level
            	int skill = 20 - (int) (20 * TFC_Core.getSkillStats(p).getSkillMultiplier(Global.SKILL_AGRICULTURE));
            	seed.stackSize = 1 + (worldObj.rand.nextInt(1 + skill) == 0 ? 1 : 0);
            	
            	//Drops seeds
            	entityitem = new EntityItem(worldObj, event.x + 0.5, event.y + 0.5, event.z + 0.5, seed);
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
                
            	//Increases Agriculture skill level.
            TFC_Core.getSkillStats(p).increaseSkill(Global.SKILL_AGRICULTURE, 1);
            
            	//Applies achievement for harvesting wild vegetables.
            if (TFC_Core.isSoil(worldObj.getBlock(event.x, event.y - 1, event.z)))
				p.addStat(TFC_Achievements.achWildVegetable, 1);
		}
	}
}
