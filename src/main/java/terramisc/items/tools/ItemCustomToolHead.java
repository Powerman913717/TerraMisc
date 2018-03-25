package terramisc.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;

public class ItemCustomToolHead extends ItemMiscToolHead
{
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		this.itemIcon = registerer.registerIcon("tfcm:toolheads/" + name);
	}
}
