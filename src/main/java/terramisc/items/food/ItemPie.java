package terramisc.items.food;

import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.ItemMeal;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.FoodRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemPie extends ItemMeal {
    public ItemPie() {
        super();
        this.hasSubtypes = true;
        this.metaNames = new String[]{"Pie", "PieApple", "PieBlackberry", "PieBlueberry",
                "PieCherry", "PiePeach", "PieLemon", "PiePeach",
                "PiePumpkin", "PieRaspberry", "PieStrawberry"};
        this.metaIcons = new IIcon[10];
        this.setFolder("food/");
    }

    @Override
    public String getItemStackDisplayName(ItemStack is) {
        StringBuilder name = new StringBuilder();

        return name.append(getPieContentsString(is)).toString() + " " + (TFC_Core.translate(this.getUnlocalizedName(is) + ".name"));
    }

    protected String getPieContentsString(ItemStack is) {
        int[] fg = Food.getFoodGroups(is);

        if (fg[1] != -1)
            return (localize2(fg[1]));

        return null;
    }

    protected String localize2(int id) {
        Item is = FoodRegistry.getInstance().getFood(id);

        if (is == null) {
            return "";
        } else
            return TFC_Core.translate(is.getUnlocalizedName() + ".name");
    }

    @Override
    protected void addFGInformation(ItemStack is, List<String> arraylist) {
        int[] fg = Food.getFoodGroups(is);
        for (int i = 0; i < fg.length; i++) {
            if (i == 5 && fg[5] == fg[0])
                return;
            if (fg[i] != -1)
                arraylist.add(localize(fg[i]));
        }
    }

    @Override
    protected float getEatAmount(FoodStatsTFC fs, float amount) {
        float eatAmount = Math.min(amount, 10);
        float stomachDiff = fs.stomachLevel + eatAmount - fs.getMaxStomach(fs.player);
        if (stomachDiff > 0)
            eatAmount -= stomachDiff;
        return eatAmount;
    }

    @Override
    protected float getFillingBoost() {
        return 1.45f;
    }

    @Override
    protected float[] getFoodWeights() {
        return new float[]{2f, 3f, 2f, 2f, 1f};
    }

    @Override
    public float getFoodMaxWeight(ItemStack is) {
        return 10;
    }

    @Override
    public boolean renderDecay() {
        return true;
    }

    @Override
    public boolean renderWeight() {
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(createTag(new ItemStack(this, 1)));
    }

    //Creates empty food to prevent NBT errors when food is loaded in NEI
    public static ItemStack createTag(ItemStack is) {
        ItemMeal.createTag(is);
        int[] foodGroups = new int[]{-1, -1, -1, -1};
        Food.setFoodGroups(is, foodGroups);
        return is;
    }

    //Add method for potion effects to be granted; maybe speed for the pies?
}
