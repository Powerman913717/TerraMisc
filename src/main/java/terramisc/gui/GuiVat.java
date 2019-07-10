package terramisc.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.GUI.GuiContainerTFC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import terramisc.containers.ContainerVat;
import terramisc.core.TFCMDetails;
import terramisc.tileentities.TEVat;

public class GuiVat extends GuiContainerTFC
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(TFCMDetails.ModID, Reference.ASSET_PATH_GUI + "gui_Vat.png");
	private TEVat vatTE;

	
	public GuiVat(InventoryPlayer inventoryplayer, TEVat te, World world, int x, int y, int z)
	{
		super(new ContainerVat(inventoryplayer, te, world, x, y, z), 176, 85);
		vatTE = te;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		createButtons();
	}

	@SuppressWarnings("unchecked")
	public void createButtons()
	{
		buttonList.clear();
		
		if (vatTE.mode == TEVat.MODE_IN)
			buttonList.add(new GuiVatTabButton(2, guiLeft + 121, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
		else if (vatTE.mode == TEVat.MODE_OUT)
			buttonList.add(new GuiVatTabButton(2, guiLeft + 121, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
	}
	
	public class GuiVatTabButton extends GuiButton
	{
		private GuiVat screen;
		private IIcon buttonicon;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 15;

		public GuiVatTabButton(int index, int xPos, int yPos, int width, int height, GuiVat gui, IIcon icon, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			buttonicon = icon;
		}

		public GuiVatTabButton(int index, int xPos, int yPos, int width, int height, GuiVat gui, String s, int xp, int yp, int xs, int ys)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			this.xPos = xp;
			this.yPos = yp;
			xSize = xs;
			ySize = ys;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				TFC_Core.bindTexture(GuiVat.TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if (buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition + 12, this.yPosition + 4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if(field_146123_n)
				{
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 2)
		{
			vatTE.actionMode();
			createButtons();
		}
	}
	
	/*
	 * Heavily based on drawGuiContainerBackgroundLayer() from GuiBarrel.java
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		bindTexture(TEXTURE);
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());

		int scale = 0;
		if(vatTE != null && vatTE.fluid != null)
		{
			scale = vatTE.getLiquidScaled(50);
			IIcon liquidIcon = vatTE.fluid.getFluid().getIcon(vatTE.fluid);
			TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
			int color = vatTE.fluid.getFluid().getColor(vatTE.fluid);
			GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) ((0xaa) & 255));
			int div = (int) Math.floor(scale / 8);
			int rem = scale - (div * 8);
			this.drawTexturedModelRectFromIcon(guiLeft + 156, guiTop + 65 - scale, liquidIcon, 8, div > 0 ? 8 : rem);
			for (int c = 0; div > 0 && c < div; c++)
			{
				this.drawTexturedModelRectFromIcon(guiLeft + 156, guiTop + 65 - (8 + (c * 8)), liquidIcon, 8, 8);
			}
			GL11.glColor3f(0, 0, 0);
		}
		
		this.drawForeground(guiLeft, guiTop);
		
		//ItemStack inStack = vatTE.getStackInSlot(0);

		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(this.mouseInRegion(156, 15, 9, 50, mouseX, mouseY))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(vatTE.getFluidLevel() + "mB");
			this.drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop + 8, this.fontRendererObj);
		}
	}
	
	/**
	 * Draws temperature arrow and other moving progress bars.
	 */
	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if(vatTE != null) // Fixes OpenEye-reported NPE
		{
			//Draw temperature indicator arrow
			int scale = vatTE.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 30, guiTop + 65 - scale, 185, 31, 15, 6);
			
			/*
			 * Draw flame indicator icon
			 * TODO Only draw the indicator with a valid recipe at an apprioriate temp.
			 * 		Scale the level of the flame indicator to denote crafting progress.
			 */
			if(vatTE.canProcess())
			{
				int i1 = this.vatTE.getTemperatureScaled(13);
	            this.drawTexturedModalRect(guiLeft + 80, guiTop + 61 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			}
		}
	}
}