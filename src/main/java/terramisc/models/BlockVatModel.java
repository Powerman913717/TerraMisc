package terramisc.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockVatModel extends ModelBase {
    //fields
    ModelRenderer Leg_01;
    ModelRenderer Leg_02;
    ModelRenderer Leg_03;
    ModelRenderer Leg_04;
    ModelRenderer Base;
    ModelRenderer Side_01;
    ModelRenderer Side_02;
    ModelRenderer Side_03;
    ModelRenderer Side_04;
    ModelRenderer Hook_01;
    ModelRenderer Hook_02;
    ModelRenderer Handle;
    ModelRenderer Firepit;

    public BlockVatModel() {
        textureWidth = 128;
        textureHeight = 64;

        Leg_01 = new ModelRenderer(this, 0, 0);
        Leg_01.addBox(0F, 0F, 0F, 1, 3, 1);
        Leg_01.setRotationPoint(6F, 22F, 6F);
        Leg_01.setTextureSize(128, 64);
        Leg_01.mirror = true;
        setRotation(Leg_01, 0F, 0F, 0F);
        Leg_02 = new ModelRenderer(this, 4, 0);
        Leg_02.addBox(0F, 0F, 0F, 1, 3, 1);
        Leg_02.setRotationPoint(6F, 22F, -7F);
        Leg_02.setTextureSize(128, 64);
        Leg_02.mirror = true;
        setRotation(Leg_02, 0F, 0F, 0F);
        Leg_03 = new ModelRenderer(this, 8, 0);
        Leg_03.addBox(0F, 0F, 0F, 1, 3, 1);
        Leg_03.setRotationPoint(-7F, 22F, -7F);
        Leg_03.setTextureSize(128, 64);
        Leg_03.mirror = true;
        setRotation(Leg_03, 0F, 0F, 0F);
        Leg_04 = new ModelRenderer(this, 12, 0);
        Leg_04.addBox(0F, 0F, 0F, 1, 3, 1);
        Leg_04.setRotationPoint(-7F, 22F, 6F);
        Leg_04.setTextureSize(128, 64);
        Leg_04.mirror = true;
        setRotation(Leg_04, 0F, 0F, 0F);

        Base = new ModelRenderer(this, 0, 4);
        Base.addBox(0F, 0F, 0F, 14, 1, 14);
        Base.setRotationPoint(-7F, 21F, -7F);
        Base.setTextureSize(128, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);


        Side_01 = new ModelRenderer(this, 0, 18);
        Side_01.addBox(0F, 0F, 0F, 1, 10, 12);
        Side_01.setRotationPoint(6F, 11F, -6F);
        Side_01.setTextureSize(128, 64);
        Side_01.mirror = true;
        setRotation(Side_01, 0F, 0F, 0F);

        Side_02 = new ModelRenderer(this, 0, 18);
        Side_02.addBox(0F, 0F, 0F, 1, 10, 12);
        Side_02.setRotationPoint(-7F, 11F, -6F);
        Side_02.setTextureSize(128, 64);
        Side_02.mirror = true;
        setRotation(Side_02, 0F, 0F, 0F);

        Side_03 = new ModelRenderer(this, 0, 18);
        Side_03.addBox(0F, 0F, 0F, 14, 10, 1);
        Side_03.setRotationPoint(-7F, 11F, -7F);
        Side_03.setTextureSize(128, 64);
        Side_03.mirror = true;
        setRotation(Side_03, 0F, 0F, 0F);

        Side_04 = new ModelRenderer(this, 0, 18);
        Side_04.addBox(0F, 0F, 0F, 14, 10, 1);
        Side_04.setRotationPoint(-7F, 11F, 6F);
        Side_04.setTextureSize(128, 64);
        Side_04.mirror = true;
        setRotation(Side_04, 0F, 0F, 0F);

        Hook_01 = new ModelRenderer(this, 0, 41);
        Hook_01.addBox(0F, 0F, 0F, 1, 3, 3);
        Hook_01.setRotationPoint(7F, 10F, -1F);
        Hook_01.setTextureSize(128, 64);
        Hook_01.mirror = true;
        setRotation(Hook_01, 0F, 0F, 0F);
        Hook_02 = new ModelRenderer(this, 0, 41);
        Hook_02.addBox(0F, 0F, 0F, 1, 3, 3);
        Hook_02.setRotationPoint(-8F, 10F, -1F);
        Hook_02.setTextureSize(128, 64);
        Hook_02.mirror = true;

        setRotation(Hook_02, 0F, 0F, 0F);
        Handle = new ModelRenderer(this, 0, 47);
        Handle.addBox(0F, 0F, 0F, 16, 1, 1);
        Handle.setRotationPoint(-8F, 9F, 0F);
        Handle.setTextureSize(128, 64);
        Handle.mirror = true;
        setRotation(Handle, 0F, 0F, 0F);

        Firepit = new ModelRenderer(this, 96, 48);
        Firepit.addBox(0F, 0F, 0F, 16, 0, 16);
        Firepit.setRotationPoint(-8F, 23.9F, -8F);
        Firepit.setTextureSize(128, 64);
        Firepit.mirror = true;
        setRotation(Firepit, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Leg_01.render(f5);
        Leg_02.render(f5);
        Leg_03.render(f5);
        Leg_04.render(f5);
        Base.render(f5);
        Side_01.render(f5);
        Side_02.render(f5);
        Side_03.render(f5);
        Side_04.render(f5);
        Hook_01.render(f5);
        Hook_02.render(f5);
        Handle.render(f5);
    }

    public void renderModel(float f) {
        Leg_01.render(f);
        Leg_02.render(f);
        Leg_03.render(f);
        Leg_04.render(f);
        Base.render(f);
        Side_01.render(f);
        Side_02.render(f);
        Side_03.render(f);
        Side_04.render(f);
        Hook_01.render(f);
        Hook_02.render(f);
        Handle.render(f);
        Firepit.render(f);
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
