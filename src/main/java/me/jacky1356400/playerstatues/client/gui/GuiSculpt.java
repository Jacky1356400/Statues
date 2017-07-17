package me.jacky1356400.playerstatues.client.gui;

import java.util.Random;

import me.jacky1356400.playerstatues.PlayerStatues;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import me.jacky1356400.playerstatues.StatueParameters;
import me.jacky1356400.playerstatues.client.renderer.RenderPlayerStatue;
import me.jacky1356400.playerstatues.entities.EntityStatuePlayer;
import me.jacky1356400.playerstatues.network.Packets;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiSculpt extends GuiScreenPlus
{
	private Random rand = new Random();
	private EntityStatuePlayer player = null;
	private RenderPlayerStatue renderer = new RenderPlayerStatue();
	public static StatueParameters pose = new StatueParameters();

	private GuiEdit skinNameEdit;
	private int worldX, worldY, worldZ;
	private int face;
	private IBlockState state;
	private int mouseX;
	private int mouseY;

	private Gui2dScroller scrollerAR;
	private Gui2dScroller scrollerAL;
	private Gui2dScroller scrollerLR;
	private Gui2dScroller scrollerLL;
	private Gui2dScroller scrollerH;
	private Gui2dScroller scrollerB;

	static String skinName = "";

	public GuiSculpt(World world, int px, int py, int pz, final EntityPlayer entityplayer, int facing)
	{
		super(227, 228, "playerstatues:textures/gui-sculpt.png");
		worldX = px;
		worldY = py;
		worldZ = pz;
		face = facing;

		state = world.getBlockState(new BlockPos(worldX, worldY, worldZ));

		addChild(new GuiLabel(63, 136, "Player name:"));
		addChild(skinNameEdit = new GuiEdit(0, 62, 150, 102, 13));
		skinNameEdit.setText(skinName);

		addChild(new GuiExButton(9, 174, 209, 20, "Randomize")
		{
			@Override
			public void onClick()
			{
				scrollerAR.set(rand.nextDouble(), rand.nextDouble());
				scrollerAL.set(rand.nextDouble(), rand.nextDouble());
				scrollerLR.set(rand.nextDouble(), rand.nextDouble());
				scrollerLL.set(rand.nextDouble(), rand.nextDouble());
				scrollerH.set(rand.nextDouble(), rand.nextDouble());
				scrollerB.set(rand.nextDouble(), rand.nextDouble());
			}
		});

		addChild(new GuiExButton(9, 199, 209, 20, "Sculpt!")
		{
			@Override
			public void onClick()
			{
				skinName = skinNameEdit.getText();

				try
				{
					Packet asculpt = PlayerStatues.packet.create(Packets.SCULPTURE_CREATION).writeInt(worldX).writeInt(worldY).writeInt(worldZ).writeByte((byte) face).writeString(skinName);

					pose.write(asculpt);

					PlayerStatues.packet.sendToServer(asculpt);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
				mc.thePlayer.closeScreen();
			}
		});

		addChild(scrollerAR = new Gui2dScroller(9, 9, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, pose.armRightA, 1.0f - pose.armRightB)
		{
			@Override
			void onChange()
			{
				pose.armRightA = (float) u;
				pose.armRightB = 1.0f - (float) v;
			}
		});

		addChild(scrollerAL = new Gui2dScroller(171, 9, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, 1.0f - pose.armLeftA, 1.0f - pose.armLeftB)
		{
			@Override
			void onChange()
			{
				pose.armLeftA = 1.0f - (float) u;
				pose.armLeftB = 1.0f - (float) v;
			}
		});

		addChild(scrollerLR = new Gui2dScroller(9, 63, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, pose.legRightA, 1.0f - pose.legRightB)
		{
			@Override
			void onChange()
			{
				pose.legRightA = (float) u;
				pose.legRightB = 1.0f - (float) v;
			}
		});

		addChild(scrollerLL = new Gui2dScroller(171, 63, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, 1.0f - pose.legLeftA, 1.0f - pose.legLeftB)
		{
			@Override
			void onChange()
			{
				pose.legLeftA = 1.0f - (float) u;
				pose.legLeftB = 1.0f - (float) v;
			}
		});

		addChild(scrollerH = new Gui2dScroller(9, 117, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, pose.headA, pose.headB)
		{
			@Override
			void onChange()
			{
				pose.headA = (float) u;
				pose.headB = (float) v;
			}
		});

		addChild(scrollerB = new Gui2dScroller(171, 117, 47, 47, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, pose.bodyA, pose.bodyB)
		{
			@Override
			void onChange()
			{
				pose.bodyA = (float) u;
				pose.bodyB = (float) v;
			}
		});

		player = new EntityStatuePlayer(Minecraft.getMinecraft().theWorld, "");
		player.setPose(pose);

		renderer.setRenderManager(RenderManager.instance);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float fx, int fy, int a)
	{
		super.drawGuiContainerBackgroundLayer(fx, fy, a);

		player.applySkin(skinNameEdit.getText(), state.getBlock(), 2, state.getBlock().getMetaFromState(state));
		player.ticksExisted = 10;

		int x = guiLeft + 114;
		int y = guiTop + 110;
		renderPlayerModel(x, y, 45, 0, 0);
	}

	public void renderPlayerModel(int par0, int par1, int par2, float par3, float par4)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(par0, par1, 50.0F);
		GL11.glScalef((-par2), par2, par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);

		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((par4 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		player.prevRenderYawOffset = player.renderYawOffset = (float) Math.atan((par3 / 40.0F)) * 20.0F;
		player.prevRotationYaw = player.rotationYaw = (float) Math.atan((par3 / 40.0F)) * 40.0F;
		player.prevRotationPitch = player.rotationPitch = -((float) Math.atan((par4 / 40.0F))) * 20.0F;
		player.prevRotationYawHead = player.rotationYawHead = player.prevRotationYaw;
		GL11.glTranslated(0.0, player.getYOffset(), 0.0);
		renderer.doRender(player, 0, 0, 0, 0, 0.01f);
		GL11.glPopMatrix();

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

}
