package terramisc.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockBrickOvenModel extends ModelBase {
    //fields
    ModelRenderer Base;
    ModelRenderer Support_L;
    ModelRenderer Support_R;
    ModelRenderer Crossmember0;
    ModelRenderer Crossmember1;
    ModelRenderer Side0;
    ModelRenderer Side1;
    ModelRenderer Back;
    ModelRenderer Top;
    ModelRenderer Corner_TL;
    ModelRenderer Corner_TR;
    ModelRenderer Corner_BL;
    ModelRenderer Corner_BR;
    ModelRenderer Fire;
    ModelRenderer Panel;

    public BlockBrickOvenModel() {
        textureWidth = 64;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-8F, 0F, -8F, 16, 1, 16);
        Base.setRotationPoint(0F, 23F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Support_L = new ModelRenderer(this, 0, 17);
        Support_L.addBox(0F, 0F, 0F, 2, 14, 1);
        Support_L.setRotationPoint(-7F, 9F, -8F);
        Support_L.setTextureSize(64, 64);
        Support_L.mirror = true;
        setRotation(Support_L, 0F, 0F, 0F);
        Support_R = new ModelRenderer(this, 6, 17);
        Support_R.addBox(0F, 0F, 0F, 2, 14, 1);
        Support_R.setRotationPoint(5F, 9F, -8F);
        Support_R.setTextureSize(64, 64);
        Support_R.mirror = true;
        setRotation(Support_R, 0F, 0F, 0F);
        Crossmember0 = new ModelRenderer(this, 12, 17);
        Crossmember0.addBox(0F, 0F, 0F, 10, 2, 1);
        Crossmember0.setRotationPoint(-5F, 9F, -8F);
        Crossmember0.setTextureSize(64, 64);
        Crossmember0.mirror = true;
        setRotation(Crossmember0, 0F, 0F, 0F);
        Crossmember1 = new ModelRenderer(this, 12, 20);
        Crossmember1.addBox(0F, 0F, 0F, 10, 3, 1);
        Crossmember1.setRotationPoint(-5F, 15F, -8F);
        Crossmember1.setTextureSize(64, 64);
        Crossmember1.mirror = true;
        setRotation(Crossmember1, 0F, 0F, 0F);
        Side0 = new ModelRenderer(this, 0, 32);
        Side0.addBox(0F, 0F, 0F, 1, 14, 16);
        Side0.setRotationPoint(-8F, 9F, -8F);
        Side0.setTextureSize(64, 64);
        Side0.mirror = true;
        setRotation(Side0, 0F, 0F, 0F);
        Side1 = new ModelRenderer(this, 0, 32);
        Side1.addBox(0F, 0F, -7F, 1, 14, 16);
        Side1.setRotationPoint(7F, 9F, -1F);
        Side1.setTextureSize(64, 64);
        Side1.mirror = true;
        setRotation(Side1, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 2, 47);
        Back.addBox(0F, 0F, 0F, 14, 14, 1);
        Back.setRotationPoint(-7F, 9F, 7F);
        Back.setTextureSize(64, 64);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 0);
        Top.addBox(0F, 0F, 0F, 16, 1, 16);
        Top.setRotationPoint(-8F, 8F, -8F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Corner_TL = new ModelRenderer(this, 12, 27);
        Corner_TL.addBox(0F, 0F, 0F, 2, 2, 1);
        Corner_TL.setRotationPoint(-5F, 11F, -8F);
        Corner_TL.setTextureSize(64, 64);
        Corner_TL.mirror = true;
        setRotation(Corner_TL, 0F, 0F, 0F);
        Corner_TR = new ModelRenderer(this, 12, 24);
        Corner_TR.addBox(0F, 0F, 0F, 2, 2, 1);
        Corner_TR.setRotationPoint(3F, 11F, -8F);
        Corner_TR.setTextureSize(64, 64);
        Corner_TR.mirror = true;
        setRotation(Corner_TR, 0F, 0F, 0F);
        Corner_BL = new ModelRenderer(this, 18, 27);
        Corner_BL.addBox(0F, 0F, 0F, 3, 2, 1);
        Corner_BL.setRotationPoint(-5F, 18F, -8F);
        Corner_BL.setTextureSize(64, 64);
        Corner_BL.mirror = true;
        setRotation(Corner_BL, 0F, 0F, 0F);
        Corner_BR = new ModelRenderer(this, 18, 24);
        Corner_BR.addBox(0F, 0F, 0F, 3, 2, 1);
        Corner_BR.setRotationPoint(2F, 18F, -8F);
        Corner_BR.setTextureSize(64, 64);
        Corner_BR.mirror = true;
        setRotation(Corner_BR, 0F, 0F, 0F);
        Fire = new ModelRenderer(this, 42, 57);
        Fire.addBox(0F, 0F, 0F, 10, 5, 1);
        Fire.setRotationPoint(-5F, 18F, -7F);
        Fire.setTextureSize(64, 64);
        Fire.mirror = true;
        setRotation(Fire, 0F, 0F, 0F);
        Panel = new ModelRenderer(this, 42, 17);
        Panel.addBox(0F, 0F, 0F, 10, 5, 1);
        Panel.setRotationPoint(-5F, 11F, -7F);
        Panel.setTextureSize(64, 64);
        Panel.mirror = true;
        setRotation(Panel, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Base.render(f5);
        Support_L.render(f5);
        Support_R.render(f5);
        Crossmember0.render(f5);
        Crossmember1.render(f5);
        Side0.render(f5);
        Side1.render(f5);
        Back.render(f5);
        Top.render(f5);
        Corner_TL.render(f5);
        Corner_TR.render(f5);
        Corner_BL.render(f5);
        Corner_BR.render(f5);
        Fire.render(f5);
        Panel.render(f5);
    }

    public void renderModel(float f) {
        Base.render(f);
        Support_L.render(f);
        Support_R.render(f);
        Crossmember0.render(f);
        Crossmember1.render(f);
        Side0.render(f);
        Side1.render(f);
        Back.render(f);
        Top.render(f);
        Corner_TL.render(f);
        Corner_TR.render(f);
        Corner_BL.render(f);
        Corner_BR.render(f);
        Fire.render(f);
        Panel.render(f);
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
