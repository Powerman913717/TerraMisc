package terramisc.worldGen.Generators;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.api.TFCBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TECropTFCM;

import java.util.Random;

public class WorldGenGrowCropsTFCM implements IWorldGenerator {

    public WorldGenGrowCropsTFCM() {

    }

    public void generate(World world, Random rand, int x, int z, int numToGen, CropIndexTFCM crop) {
        int i = x, j = 150, k = z;
        TECropTFCM te;

        for (int c = 0; c < numToGen; c++) {
            i = x - 8 + rand.nextInt(16);
            k = z - 8 + rand.nextInt(16);
            j = world.getTopSolidOrLiquidBlock(i, k);

            if (crop != null) {
                float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, TFC_Time.getTotalDays(), i, j, k);
                int month = TFC_Time.getSeasonAdjustedMonth(k);

                if (temp > crop.minAliveTemp && month > 0 && month <= 6) {
                    Block b = world.getBlock(i, j, k);
                    if (TFCBlocks.crops.canBlockStay(world, i, j, k) && (b.isAir(world, i, j, k) || b == TFCBlocks.tallGrass)) {
                        if (world.setBlock(i, j, k, TFCMBlocks.blockCrops, 0, 0x2)) {
                            te = (TECropTFCM) world.getTileEntity(i, j, k);
                            if (te != null) {
                                te.cropId = crop.cropName;
                                float gt = Math.max(crop.growthTime / TFC_Time.daysInMonth, 0.01f);
                                float mg = Math.min(month / gt, 1.0f) * (0.75f + (rand.nextFloat() * 0.25f));
                                float growth = Math.min(crop.numGrowthStages * mg, crop.numGrowthStages);
                                te.growth = growth;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void generate(Random rand, int x, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        x *= 16;
        z *= 16;
        CropManagerTFCM manager = CropManagerTFCM.getInstance();
        int spawnSize = manager.crops.size();

        if (spawnSize > 0 && rand.nextInt(Math.max((19 / spawnSize) * 20, 20)) == 0) {
            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
            if (biome instanceof TFCBiome && biome != TFCBiome.OCEAN && biome != TFCBiome.DEEP_OCEAN) {
                CropIndexTFCM crop = manager.crops.get((rand.nextInt(spawnSize)));
                if (crop != null && crop.generate) //Only generate specific crops
                {
                    int num = 2 + rand.nextInt(8);
                    int xCoord = x + rand.nextInt(16) + 8;
                    int zCoord = z + rand.nextInt(16) + 8;
                    for (int i = 0; i < num; i++) {
                        generate(world, rand, xCoord, zCoord, 1, crop);
                    }
                }
            }
        }
    }

}