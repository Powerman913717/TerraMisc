package terramisc.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockTallowCandleModel extends ModelBase {
    //fields
    ModelRenderer CandleBody;
    ModelRenderer Wax_Drip_1;
    ModelRenderer Wax_Drip_2;
    ModelRenderer Wick;

    public BlockTallowCandleModel() {
        textureWidth = 64;
        textureHeight = 32;

        CandleBody = new ModelRenderer(this, 0, 0);
        CandleBody.addBox(-1F, 0F, -2F, 4, 6, 4);
        CandleBody.setRotationPoint(-1F, 18F, 0F);
        CandleBody.setTextureSize(64, 32);
        CandleBody.mirror = true;
        setRotation(CandleBody, 0F, 0F, 0F);
        Wax_Drip_1 = new ModelRenderer(this, 0, 10);
        Wax_Drip_1.addBox(0F, 0F, 0F, 2, 4, 2);
        Wax_Drip_1.setRotationPoint(-3F, 20F, 1F);
        Wax_Drip_1.setTextureSize(64, 32);
        Wax_Drip_1.mirror = true;
        setRotation(Wax_Drip_1, 0F, 0F, 0F);
        Wax_Drip_2 = new ModelRenderer(this, 0, 16);
        Wax_Drip_2.addBox(0F, 0F, 0F, 2, 3, 2);
        Wax_Drip_2.setRotationPoint(1F, 21F, -3F);
        Wax_Drip_2.setTextureSize(64, 32);
        Wax_Drip_2.mirror = true;
        setRotation(Wax_Drip_2, 0F, 0F, 0F);
        Wick = new ModelRenderer(this, 0, 21);
        Wick.addBox(0F, 0F, 0F, 1, 1, 1);
        Wick.setRotationPoint(-1F, 17F, -1F);
        Wick.setTextureSize(64, 32);
        Wick.mirror = true;
        setRotation(Wick, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        CandleBody.render(f5);
        Wax_Drip_1.render(f5);
        Wax_Drip_2.render(f5);
        Wick.render(f5);
    }

    public void renderModel(float f) {
        CandleBody.render(f);
        Wax_Drip_1.render(f);
        Wax_Drip_2.render(f);
        Wick.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
