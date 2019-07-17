package terramisc.items.itemblocks;

import terramisc.core.TFCMBlocks;
import net.minecraft.block.Block;

import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.dunk.tfc.api.Constant.Global;

public class ItemBlockRoadBlock extends ItemTerraBlock
{
	public ItemBlockRoadBlock(Block b)
	{
		super(b);
		if(b == TFCMBlocks.blockRoadIgEx) metaNames = Global.STONE_IGEX;
		else if(b == TFCMBlocks.blockRoadIgIn) metaNames = Global.STONE_IGIN;
		else if(b == TFCMBlocks.blockRoadSed) metaNames = Global.STONE_SED;
		else if(b == TFCMBlocks.blockRoadMM) metaNames = Global.STONE_MM;
	}
}
