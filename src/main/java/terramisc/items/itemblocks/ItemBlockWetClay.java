package terramisc.items.itemblocks;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockWetClay extends ItemTerraBlock
{

	public ItemBlockWetClay(Block b) 
	{
		super(b);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		if(((ISize)is.getItem()).getSize(is)!= null && ((ISize)is.getItem()).getWeight(is) != null && ((ISize)is.getItem()).getReach(is)!= null)
			arraylist.add("\u2696" + TFC_Core.translate("gui.Weight." + ((ISize)is.getItem()).getWeight(is).getName()) + " \u21F2" + 
					TFC_Core.translate("gui.Size." + ((ISize)is.getItem()).getSize(is).getName().replace(" ", "")));
		
		arraylist.add(TFC_Core.translate("gui.clay.baking"));
	}

}
