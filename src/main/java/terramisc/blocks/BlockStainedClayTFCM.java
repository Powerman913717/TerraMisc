package terramisc.blocks;

import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.Tools.ItemHammer;
import com.dunk.tfc.api.Tools.IToolChisel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import terramisc.items.itemblocks.ItemBlockStainedClayTFCM;

import java.util.List;

public class BlockStainedClayTFCM extends BlockTerra {
    protected IIcon[] icons;

    public BlockStainedClayTFCM(Material material) {
        super(material);
        this.setHardness(15);
        this.setResistance(10);
        this.setCreativeTab(TFCTabs.TFC_BUILDING);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < ItemBlockStainedClayTFCM.names.length; i++)
            list.add(new ItemStack(this, 1, i));
    }

    /*
     * Mapping from metadata value to damage value
     */
    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public IIcon getIcon(int i, int j) {
        if ((j & 15) >= icons.length)
            return icons[0];
        return icons[j & 15];
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer) {
        icons = new IIcon[16];

        icons[0] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay");
        icons[1] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_black");
        icons[2] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_red");
        icons[3] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_green");
        icons[4] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_brown");
        icons[5] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_blue");
        icons[6] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_purple");
        icons[7] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_cyan");
        icons[8] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_silver");
        icons[9] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_gray");
        icons[10] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_pink");
        icons[11] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_lime");
        icons[12] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_yellow");
        icons[13] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_light_blue");
        icons[14] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_magenta");
        icons[15] = iconRegisterer.registerIcon("Minecraft" + ":" + "hardened_clay_stained_orange");
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9) {
        boolean hasHammer = false;
        for (int i = 0; i < 9; i++)
            if (entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
                hasHammer = true;

        if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel &&
                hasHammer && !world.isRemote && ((IToolChisel) entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z)) {
            Block id = world.getBlock(x, y, z);
            byte meta = (byte) world.getBlockMetadata(x, y, z);

            return ((IToolChisel) entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
        }
        return false;
    }

}
