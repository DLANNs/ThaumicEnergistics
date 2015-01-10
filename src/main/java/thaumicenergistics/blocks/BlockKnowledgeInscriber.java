package thaumicenergistics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumicenergistics.ThaumicEnergistics;
import thaumicenergistics.gui.ThEGuiHandler;
import thaumicenergistics.registries.BlockEnum;
import thaumicenergistics.texture.BlockTextureManager;
import thaumicenergistics.tileentities.TileKnowledgeInscriber;
import thaumicenergistics.util.EffectiveSide;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockKnowledgeInscriber
	extends AbstractBlockAEWrenchable
{
	/**
	 * Cached value of the UP ordinal.
	 */
	private static final int SIDE_TOP = ForgeDirection.UP.ordinal();

	public BlockKnowledgeInscriber()
	{
		// Call super with material machine (iron) 
		super( Material.iron );

		// Basic hardness
		this.setHardness( 1.0f );

		// Sound of metal
		this.setStepSound( Block.soundTypeMetal );

		// Place in the ThE creative tab
		this.setCreativeTab( ThaumicEnergistics.ThETab );
	}

	/**
	 * Called when the block is broken.
	 */
	@Override
	public void breakBlock( final World world, final int x, final int y, final int z, final Block block, final int metaData )
	{
		// Is this server side?
		if( EffectiveSide.isServerSide() )
		{
			// Get the tile
			TileKnowledgeInscriber tileKI = (TileKnowledgeInscriber)world.getTileEntity( x, y, z );

			// Does the inscriber have a cell?
			if( ( tileKI != null ) && ( tileKI.hasKCore() ) )
			{
				// Spawn the core as an item entity.
				world.spawnEntityInWorld( new EntityItem( world, 0.5 + x, 0.5 + y, 0.2 + z, tileKI.getInventory().getStackInSlot(
					TileKnowledgeInscriber.KCORE_SLOT ) ) );
			}
		}

		// Call super
		super.breakBlock( world, x, y, z, block, metaData );
	}

	@Override
	public TileEntity createNewTileEntity( final World world, final int metaData )
	{
		return new TileKnowledgeInscriber();
	}

	/**
	 * Gets the standard block icon.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon( final int side, final int meta )
	{
		if( side == BlockKnowledgeInscriber.SIDE_TOP )
		{
			return BlockTextureManager.KNOWLEDGE_INSCRIBER.getTextures()[1];
		}

		// Sides + bottom
		return BlockTextureManager.KNOWLEDGE_INSCRIBER.getTextures()[0];
	}

	/**
	 * Gets the unlocalized name of the block.
	 */
	@Override
	public String getUnlocalizedName()
	{
		return BlockEnum.KNOWLEDGE_INSCRIBER.getUnlocalizedName();
	}

	/**
	 * Is opaque.
	 */
	@Override
	public final boolean isOpaqueCube()
	{
		// Occlude adjoining faces.
		return true;
	}

	/**
	 * Is solid.
	 */
	@Override
	public final boolean isSideSolid( final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection side )
	{
		// This is a solid cube
		return true;
	}

	@Override
	public final boolean onBlockActivated( final World world, final int x, final int y, final int z, final EntityPlayer player )
	{
		// Launch the gui.
		ThEGuiHandler.launchGui( ThEGuiHandler.KNOWLEDGE_INSCRIBER, player, world, x, y, z );

		return true;
	}

	/**
	 * Taken care of by texture manager
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public final void registerBlockIcons( final IIconRegister register )
	{
		// Ignored
	}

	/**
	 * Normal renderer.
	 */
	@Override
	public final boolean renderAsNormalBlock()
	{
		return true;
	}

}