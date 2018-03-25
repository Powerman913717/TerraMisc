package terramisc.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import terramisc.core.TFCMItems;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TFCMMobDropHandler 
{
	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event)
	{
		if(event.entityLiving instanceof EntityDeer)
		{
			float birth = ((IAnimal) event.entityLiving).getBirthDay();
			float time = TFC_Time.getTotalDays();
			float ageMod = (time - birth) / ((IAnimal) event.entityLiving).getNumberOfDaysToAdult();
			
			event.entityLiving.entityDropItem(new ItemStack(TFCMItems.itemDeerTendon, (int) (2 * ageMod)), 0);
		}
		
		if(event.entityLiving instanceof EntityHorseTFC)
		{
			float birth = ((IAnimal) event.entityLiving).getBirthDay();
			float time = TFC_Time.getTotalDays();
			float ageMod = (time - birth) / ((IAnimal) event.entityLiving).getNumberOfDaysToAdult();
			
			event.entityLiving.entityDropItem(new ItemStack(TFCMItems.itemDeerTendon, (int) (2 * ageMod)), 0);
		}
		
		if(event.entityLiving instanceof EntityCowTFC)
		{
			float birth = ((IAnimal) event.entityLiving).getBirthDay();
			float time = TFC_Time.getTotalDays();
			float ageMod = (time - birth) / ((IAnimal) event.entityLiving).getNumberOfDaysToAdult();
			
			event.entityLiving.entityDropItem(new ItemStack(TFCMItems.itemSuet, (int) (4 * ageMod)), 0);
		}
		
		if(event.entityLiving instanceof EntitySheepTFC)
		{
			float birth = ((IAnimal) event.entityLiving).getBirthDay();
			float time = TFC_Time.getTotalDays();
			float ageMod = (time - birth) / ((IAnimal) event.entityLiving).getNumberOfDaysToAdult();
			
			event.entityLiving.entityDropItem(new ItemStack(TFCMItems.itemSuet, (int) (3 * ageMod)), 0);
		}
		
		if(event.entityLiving instanceof EntityBear)
		{
			float birth = ((IAnimal) event.entityLiving).getBirthDay();
			float time = TFC_Time.getTotalDays();
			float ageMod = (time - birth) / ((IAnimal) event.entityLiving).getNumberOfDaysToAdult();
			
			event.entityLiving.entityDropItem(new ItemStack(TFCMItems.itemSuet, (int) (6 * ageMod)), 0);
		}

	}
}

