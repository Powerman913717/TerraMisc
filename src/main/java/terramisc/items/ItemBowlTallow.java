package terramisc.items;

import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBowlTallow extends ItemTFCM
{
	private IIcon[] icons = new IIcon[3];
	
	public ItemBowlTallow()
	{
		super(EnumSize.LARGE, EnumWeight.MEDIUM);
		this.setMaxDamage(2);
		this.hasSubtypes = true;
		this.metaNames = new String[]{"0", "1", "2"};
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		return icons[i];
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {
		icons[0] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[0]);
		icons[1] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[1]);
		icons[2] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[2]);
    }
	
	@Override
	public boolean canStack() 
	{
		return false;
	}
	
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) 
    {
        return false;
    }
    
    public boolean hasContainerItem() 
    {
    	return true;
    }
    
    public ItemStack getContainerItem(ItemStack itemStack) 
    {
    	if(itemStack.getItemDamage() < 2)
    	{
    		itemStack.attemptDamageItem(1, itemRand);
        	
        	return itemStack;
    	}
    	else
    	{
    		itemStack = new ItemStack(TFCItems.potteryBowl, 1, 1);
    		
    		return itemStack;
    	}
    	
    }
}
