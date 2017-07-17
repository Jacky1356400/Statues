package me.jacky1356400.playerstatues.entities;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import me.jacky1356400.playerstatues.StatueParameters;
import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.client.ImageStatueBufferDownload;
import me.jacky1356400.playerstatues.client.StatueTextureDownloaded;
import me.jacky1356400.playerstatues.client.StatueTextureStatic;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class EntityStatuePlayer extends EntityPlayer
{
	private static ResourceLocation emptySkin = new ResourceLocation("playerstatues:textures/steve.png");
	private String skinName = "";
	private ResourceLocation skin;
	private AbstractTexture dataSkin;

	private StatueParameters pose;

	public EntityStatuePlayer(World world, String username)
	{
		super(world, new GameProfile(UUID.fromString("a9cb469c-f43d-4925-946d-c85a90e58a15"), username));
		// Massive hack alert!
		// This makes the RendererLivingEntity checks fail, and thus
		// no nametag is rendered!
		// this.startRiding(this);
	}

	@Override
	public boolean canCommandSenderUseCommand(int i, String s)
	{
		return false;
	}

	public void applySkin(String name, Block block, int side, int meta)
	{
		if(block == null)
			block = Blocks.STONE;

		ResourceLocation steveSkin = new ResourceLocation("playerstatues:textures/steve.png|B" + Block.getIdFromBlock(block) + "," + side + "," + meta);
		AbstractTexture steveDataSkin = getDataForSteve(steveSkin, new ResourceLocation("playerstatues:textures/steve.png"), block, side, meta);

		if(name != null && !name.isEmpty())
		{
			skin = new ResourceLocation("skins/" + StringUtils.stripControlCodes(name) + "|B" + Block.getIdFromBlock(block) + "," + side + "," + meta);
			dataSkin = getTextureForSkin(skin, steveDataSkin, name, block, side, meta);
		}
		else
		{
			skin = steveSkin;
			dataSkin = steveDataSkin;
		}
	}

	public AbstractTexture getTextureSkin()
	{
		return dataSkin;
	}

	public ResourceLocation getLocationSkin()
	{
		return skin;
	}

	public AbstractTexture getTextureForSkin(ResourceLocation skin, AbstractTexture fallbackSkin, String name, Block block, int side, int meta)
	{
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		AbstractTexture tex = (AbstractTexture) texturemanager.getTexture(skin);

		if(tex == null)
		{
			tex = new StatueTextureDownloaded(skin, PlayerStatues.skinServerLocation + name + ".png", fallbackSkin, new ImageStatueBufferDownload(this, block, side, meta, name + "." + Block.getIdFromBlock(block) + "." + meta));
			texturemanager.loadTexture(skin, tex);
		}

		return tex;
	}

	public AbstractTexture getDataForSteve(ResourceLocation skin, ResourceLocation base, Block block, int side, int meta)
	{
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		AbstractTexture tex = (AbstractTexture) texturemanager.getTexture(skin);

		if(tex == null)
		{
			tex = new StatueTextureStatic(base, new ImageStatueBufferDownload(this, block, side, meta, "steve." + Block.getIdFromBlock(block) + "." + meta));
			texturemanager.loadTexture(skin, tex);
		}

		return tex;
	}

	@Override
	public void addChatMessage(ITextComponent var1)
	{
	}

	@Override
	public boolean isSpectator()
	{
		return false;
	}

	@Override
	public boolean isCreative()
	{
		return false;
	}

	public String getSkinName()
	{
		return skinName;
	}

	public void setSkinName(String skinName)
	{
		this.skinName = skinName;
	}

	public StatueParameters getPose()
	{
		return pose;
	}

	public void setPose(StatueParameters pose)
	{
		this.pose = pose;
	}
}
