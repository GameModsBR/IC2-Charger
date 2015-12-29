package br.com.gamemods.ic2.charger.charger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCharger extends BlockContainer
{
    private TextureAtlasSprite[][] icons = new TextureAtlasSprite[1][12];

    public BlockCharger()
    {
        super(Material.iron);
        setHardness(1.5f);
        setStepSound(soundTypeMetal);
        setBlockName("charger");
        setBlockTextureName("IC2:blockElectric");
    }

    @Override
    public TileEntityCharger createNewTileEntity(World world, int meta)
    {
        switch (meta)
        {
            default: return new TileEntityChargerLV();
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        TextureMap textureMap = (TextureMap)iconRegister;
        String[] names = {"ic2:wiring/blockBatBox:"};
        for(int ni=0; ni < names.length; ni++)
        {
            String name = names[ni];
            for(int active = 0; active < 2; active++)
                for(int side = 0; side < 6; side++)
                {
                    int subIndex = active * 6 + side;
                    icons[ni][subIndex] = textureMap.getTextureExtry(name+subIndex);
                }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return icons[meta][
                side == ForgeDirection.SOUTH.ordinal() || side == ForgeDirection.NORTH.ordinal()? ForgeDirection.EAST.ordinal() :
                side == ForgeDirection.EAST.ordinal()? ForgeDirection.WEST.ordinal() : side];
    }
}
