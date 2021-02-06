package terramisc.blocks;

import com.dunk.tfc.Blocks.BlockCrop;
import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.api.TFCOptions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TECropTFCM;

public class BlockCropTFCM extends BlockCrop {
    private IIcon[] iconsCayenne = new IIcon[7];
    private IIcon[] iconsCoffee = new IIcon[7];
    private IIcon[] iconsSweetPotato = new IIcon[7];
    private IIcon[] iconsHops = new IIcon[8];

    public BlockCropTFCM() {
        super();
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for (int i = 1; i < 6; i++) {

        }
        for (int i = 1; i < 7; i++) {

        }
        for (int i = 1; i < 8; i++) {
            iconsCayenne[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Pepper Cayenne (" + i + ")");
            iconsCoffee[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Coffee (" + i + ")");
            iconsSweetPotato[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Sweet Potato (" + i + ")");
        }
        for (int i = 1; i < 9; i++) {
            iconsHops[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Hops (" + i + ")");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta) {
        TECropTFCM te = (TECropTFCM) access.getTileEntity(x, y, z);
        CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(te.cropId);

        int stage = (int) Math.floor(te.growth);
        if (stage > crop.numGrowthStages)
            stage = crop.numGrowthStages;

        if ("cayenne".equals(te.cropId)) {
            return iconsCayenne[stage];
        } else if ("coffee".equals(te.cropId)) {
            return iconsCoffee[stage];
        } else if ("sweet potato".equals(te.cropId)) {
            return iconsSweetPotato[stage];
        } else if ("hops".equals(te.cropId)) {
            return iconsHops[stage];
        }
        return iconsCayenne[1];
    }

    @Override
    public int getRenderType() {
        return TFCMBlocks.cropRenderId;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TECropTFCM();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) {
        TECropTFCM te = (TECropTFCM) world.getTileEntity(x, y, z);
        CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(te.cropId);

        if (TFCOptions.enableDebugMode) {
            TerraFirmaCraft.LOG.info("TFCM:Crop ID: " + te.cropId);
            TerraFirmaCraft.LOG.info("TFCM:Growth: " + te.growth);
            TerraFirmaCraft.LOG.info("TFCM:Est Growth: " + te.getEstimatedGrowth(crop));
        }

        return false;
    }

    @Override
    public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer player) {
        TECropTFCM te = (TECropTFCM) world.getTileEntity(i, j, k);
        if (!world.isRemote) {
            ItemStack itemstack = player.inventory.getCurrentItem();
            int[] equipIDs = OreDictionary.getOreIDs(itemstack);

            for (int id : equipIDs) {
                String name = OreDictionary.getOreName(id);
                if (name.startsWith("itemScythe")) {
                    for (int x = -1; x < 2; x++) {
                        for (int z = -1; z < 2; z++) {
                            if (world.getBlock(i + x, j, k + z) == this && player.inventory.getStackInSlot(player.inventory.currentItem) != null) {
                                player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
                                TECropTFCM teX = (TECropTFCM) world.getTileEntity(i + x, j, k + z);
                                teX.onHarvest(world, player, true);

                                world.setBlockToAir(i + x, j, k + z);

                                itemstack.damageItem(1, player);
                                if (itemstack.stackSize == 0)
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                    }

                    return;
                }
            }
        }

        // Only executes if scythe wasn't found
        te.onHarvest(world, player, true);
    }
}
