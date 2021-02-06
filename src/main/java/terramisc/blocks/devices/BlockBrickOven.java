package terramisc.blocks.devices;

import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.api.TFCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import terramisc.tileentities.TEBrickOven;

import java.util.ArrayList;

public class BlockBrickOven extends BlockTerraContainer {
    public static final int BLOOMERY_TO_STACK_MAP[][] = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    public static final int SIDES_MAP[][] = {{1, 0}, {0, 1}, {1, 0}, {0, 1}};

    public BlockBrickOven() {
        super(Material.rock);
        this.setCreativeTab(TFCTabs.TFC_DEVICES);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TEBrickOven te = (TEBrickOven) world.getTileEntity(x, y, z);

            if (!canBlockStay(world, x, y, z)) {
                world.setBlockToAir(x, y, z);
                world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(this, 1)));
            } else {
                te.openGui(entityplayer);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TEBrickOven();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k) {
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
        world.setBlockToAir(i, j, k);
        world.spawnEntityInWorld(new EntityItem(world, i, j, k, new ItemStack(this, 1)));

        eject(world, i, j, k);
    }

    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex) {
        eject(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        eject(par1World, par2, par3, par4);
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        eject(par1World, par2, par3, par4);
    }

    //public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}

    public void eject(World par1World, int par2, int par3, int par4) {
        if (par1World.getTileEntity(par2, par3, par4) instanceof TEBrickOven) {
            TEBrickOven te = (TEBrickOven) par1World.getTileEntity(par2, par3, par4);
            te.ejectContents();
            par1World.removeTileEntity(par2, par3, par4);
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z) & 4;
        if (meta == 0)
            return 0;
        else
            return 15;
    }

    //MultiBlock TODO
    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z))
            return true;

        if (world.getTileEntity(x, y, z) instanceof TEBrickOven) // Prevent ClassCastException
        {
            boolean flipped = false;
            int dir = world.getBlockMetadata(x, y, z) & 3;
            TEBrickOven te = (TEBrickOven) world.getTileEntity(x, y, z);

            if (te != null)
                flipped = te.isFlipped;

            if (checkStack(world, x, y, z, dir)) {
                if (checkVertical(world, x, y, z, flipped)) {
                    if (checkHorizontal(world, x, y, z, flipped))
                        return true;
                } else if (te != null && !flipped) {
                    this.tryFlip(world, x, y, z);
                    flipped = te.isFlipped;
                    if (checkVertical(world, x, y, z, flipped)) {
                        if (checkHorizontal(world, x, y, z, flipped))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkStack(World world, int x, int y, int z, int dir) {
        int[] map = BLOOMERY_TO_STACK_MAP[dir];
        int centerX = x + map[0];
        int centerZ = z + map[1];
        if (isNorthStackValid(world, centerX, y, centerZ - 1) || centerX == x && centerZ - 1 == z) {
            if (isSouthStackValid(world, centerX, y, centerZ + 1) || centerX == x && centerZ + 1 == z) {
                if (isEastStackValid(world, centerX - 1, y, centerZ) || centerX - 1 == x && centerZ == z) {
                    if (isWestStackValid(world, centerX + 1, y, centerZ) || centerX + 1 == x && centerZ == z) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isNorthStackValid(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
                world.getBlock(x, y, z).getMaterial() == Material.iron) &&
                world.getBlock(x, y, z).isNormalCube() ||
                TFC_Core.isSouthFaceSolid(world, x, y, z); //Since its the North Block, we need to make sure the South side facing the stack is solid
    }

    private boolean isSouthStackValid(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
                world.getBlock(x, y, z).getMaterial() == Material.iron) &&
                world.getBlock(x, y, z).isNormalCube() ||
                TFC_Core.isNorthFaceSolid(world, x, y, z);//Since its the South Block, we need to make sure the North side facing the stack is solid
    }

    private boolean isEastStackValid(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
                world.getBlock(x, y, z).getMaterial() == Material.iron) &&
                world.getBlock(x, y, z).isNormalCube() ||
                TFC_Core.isWestFaceSolid(world, x, y, z);//Since its the East Block, we need to make sure the West side facing the stack is solid
    }

    private boolean isWestStackValid(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
                world.getBlock(x, y, z).getMaterial() == Material.iron) &&
                world.getBlock(x, y, z).isNormalCube() ||
                TFC_Core.isEastFaceSolid(world, x, y, z); //Since its the West Block, we need to make sure the East side facing the stack is solid
    }

    private boolean checkHorizontal(World world, int x, int y, int z, boolean flip) {
        int dir = world.getBlockMetadata(x, y, z) & 3;

        if (flip)
            dir = flipDir(dir);

        int[] map = SIDES_MAP[dir];

        boolean l = false;
        boolean r = false;
        if ((world.getBlock(x - map[0], y, z - map[1]).getMaterial() == Material.rock || world.getBlock(x - map[0], y, z - map[1]).getMaterial() == Material.iron) && world.getBlock(x - map[0], y, z - map[1]).isOpaqueCube())
            l = true;

        if (!l && world.getBlock(x - map[0], y, z - map[1]) == TFCBlocks.detailed || world.getBlock(x - map[0], y, z - map[1]) == TFCBlocks.stoneSlabs) {
            switch (dir) {
                case 0:
                    if (TFC_Core.isNorthFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]))
                        l = true;
                    break;
                case 1:
                    if (TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]))
                        l = true;
                    break;
                case 2:
                    if (TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]))
                        l = true;
                    break;
                case 3:
                    if (TFC_Core.isWestFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]))
                        l = true;
                    break;
            }

            if (!TFC_Core.isBottomFaceSolid(world, x - map[0], y, z - map[1]))
                l = false;
            if (!TFC_Core.isTopFaceSolid(world, x - map[0], y, z - map[1]))
                l = false;
        }

        if ((world.getBlock(x + map[0], y, z + map[1]).getMaterial() == Material.rock || world.getBlock(x + map[0], y, z + map[1]).getMaterial() == Material.iron) && world.getBlock(x + map[0], y, z + map[1]).isOpaqueCube())
            r = true;

        if (!r && world.getBlock(x + map[0], y, z + map[1]) == TFCBlocks.detailed || world.getBlock(x + map[0], y, z + map[1]) == TFCBlocks.stoneSlabs) {
            switch (dir) {
                case 0:
                    if (TFC_Core.isNorthFaceSolid(world, x + map[0], y, z + map[1]) && TFC_Core.isWestFaceSolid(world, x + map[0], y, z + map[1]))
                        r = true;
                    break;
                case 1:
                    if (TFC_Core.isEastFaceSolid(world, x + map[0], y, z + map[1]) && TFC_Core.isNorthFaceSolid(world, x + map[0], y, z + map[1]))
                        r = true;
                    break;
                case 2:
                    if (TFC_Core.isSouthFaceSolid(world, x + map[0], y, z + map[1]) && TFC_Core.isWestFaceSolid(world, x + map[0], y, z + map[1]))
                        r = true;
                    break;
                case 3:
                    if (TFC_Core.isWestFaceSolid(world, x + map[0], y, z + map[1]) && TFC_Core.isNorthFaceSolid(world, x + map[0], y, z + map[1]))
                        r = true;
                    break;
            }
        }

        if (!TFC_Core.isBottomFaceSolid(world, x + map[0], y, z + map[1]))
            r = false;
        if (!TFC_Core.isTopFaceSolid(world, x + map[0], y, z + map[1]))
            r = false;

        return l && r;

    }

    private boolean checkVertical(World world, int x, int y, int z, boolean flip) {
        int dir = world.getBlockMetadata(x, y, z) & 3;

        if (flip)
            dir = flipDir(dir);

        boolean b = false;
        boolean t = false;
        if ((world.getBlock(x, y - 1, z).getMaterial() == Material.rock || world.getBlock(x, y - 1, z).getMaterial() == Material.iron) && world.getBlock(x, y - 1, z).isOpaqueCube())
            b = true;

        if ((!b && world.getBlock(x, y - 1, z) == TFCBlocks.detailed || world.getBlock(x, y - 1, z) == TFCBlocks.stoneSlabs) && world.getBlock(x, y - 1, z) == TFCBlocks.firepit) {
            switch (dir) {
                case 0:
                    if (TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isWestFaceSolid(world, x, y - 1, z))
                        b = true;
                    break;
                case 1:
                    if (TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isSouthFaceSolid(world, x, y - 1, z))
                        b = true;
                    break;
                case 2:
                    if (TFC_Core.isSouthFaceSolid(world, x, y - 1, z) && TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isWestFaceSolid(world, x, y - 1, z))
                        b = true;
                    break;
                case 3:
                    if (TFC_Core.isWestFaceSolid(world, x, y - 1, z) && TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isSouthFaceSolid(world, x, y - 1, z))
                        b = true;
                    break;
            }

			/*if(!TFC_Core.isTopFaceSolid(world, x, y - 1, z))
				b = false; */
        }

        if ((world.getBlock(x, y + 1, z).getMaterial() == Material.rock || world.getBlock(x, y + 1, z).getMaterial() == Material.iron) && world.getBlock(x, y + 1, z).isOpaqueCube())
            t = true;

        if (!t && world.getBlock(x, y + 1, z) == TFCBlocks.detailed || world.getBlock(x, y + 1, z) == TFCBlocks.stoneSlabs) {
            switch (dir) {
                case 0:
                    if (TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isWestFaceSolid(world, x, y + 1, z))
                        t = true;
                    break;
                case 1:
                    if (TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isSouthFaceSolid(world, x, y + 1, z))
                        t = true;
                    break;
                case 2:
                    if (TFC_Core.isSouthFaceSolid(world, x, y + 1, z) && TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isWestFaceSolid(world, x, y + 1, z))
                        t = true;
                    break;
                case 3:
                    if (TFC_Core.isWestFaceSolid(world, x, y + 1, z) && TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isSouthFaceSolid(world, x, y + 1, z))
                        t = true;
                    break;
            }

            if (!TFC_Core.isBottomFaceSolid(world, x, y + 1, z) || !TFC_Core.isTopFaceSolid(world, x, y + 1, z))
                t = false;
        }

        return b && t;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return canBlockStay(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack is) {
        if (!world.isRemote) {
            int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
            //l = flipDir(l);
            world.setBlockMetadataWithNotify(x, y, z, dir, 0x2);
            world.scheduleBlockUpdate(x, y - 1, z, TFCBlocks.firepit, 0);
            if (!canBlockStay(world, x, y, z)) {
                world.setBlockToAir(x, y, z);
                dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!canBlockStay(world, x, y, z)) {
            if (!tryFlip(world, x, y, z)) {
                world.setBlockToAir(x, y, z);
                dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
            } else if (!canBlockStay(world, x, y, z)) {
                world.setBlockToAir(x, y, z);
                dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
            }
        }
    }

    public static int flipDir(int dir) {
        int out = 0;
        if (dir - 2 >= 0)
            out = dir - 2;
        else if (dir + 2 < 4)
            out = dir + 2;
        return out;
    }

    private boolean tryFlip(World world, int x, int y, int z) {
        TEBrickOven te = (TEBrickOven) world.getTileEntity(x, y, z);
        te.swapFlipped();
        return canBlockStay(world, x, y, z);
    }

    /***********************************************************************************
     *
     * Client Only Code Below This Point
     *
     ***********************************************************************************/

    /**
     * Displays a flat icon image for an ItemStack containing the block, instead of a render. Using primarily for WAILA HUD.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return world.getBlock(x, y, z) == this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegisterer) {
        this.blockIcon = iconRegisterer.registerIcon("tfcm:textures/models/BrickOven_off.png");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            if (/*TFCOptions.use2DGrill && */side == 0 || side == 1) {
                return blockIcon;
            }
            return TFC_Textures.sheetWroughtIron;
        }
        return blockIcon;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }
}
