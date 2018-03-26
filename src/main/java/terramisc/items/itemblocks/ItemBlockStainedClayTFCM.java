package terramisc.items.itemblocks;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import terramisc.core.TFCMBlocks;

public class ItemBlockStainedClayTFCM extends ItemTerraBlock
{
	public String[] metaNames;
	public static String[] names = {"hardened_clay", "hardened_clay_stained_black", "hardened_clay_stained_red",
			"hardened_clay_stained_green", "hardened_clay_stained_brown", "hardened_clay_stained_blue", "hardened_clay_stained_purple",
			"hardened_clay_stained_cyan", "hardened_clay_stained_silver", "hardened_clay_stained_gray", "hardened_clay_stained_pink",
			"hardened_clay_stained_lime", "hardened_clay_stained_yellow", "hardened_clay_stained_light_blue", "hardened_clay_stained_magenta",
			"hardened_clay_stained_orange"};
	
	@SuppressWarnings("static-access")
	public ItemBlockStainedClayTFCM(Block b)
	{
		super(b);
		
		if(b == TFCMBlocks.blockStainedClay)
		{
			metaNames = this.names;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		try
		{
			if(metaNames != null && is.getItemDamage() < metaNames.length)
				return getUnlocalizedName().concat("." + metaNames[is.getItemDamage()]);
		}
		catch(Exception ex)
		{
			TerraFirmaCraft.LOG.error(ex.getLocalizedMessage());
		}

		return super.getUnlocalizedName(is);
	}
}
