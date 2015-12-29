package br.com.gamemods.ic2.charger.charger;

import br.com.gamemods.ic2.charger.ChargerMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockCharger extends BlockContainer
{
    private TextureAtlasSprite[][] icons = new TextureAtlasSprite[4][12];

    public BlockCharger()
    {
        super(Material.iron);
        setHardness(1.5f);
        setStepSound(soundTypeMetal);
        setBlockName("charger");
        setBlockTextureName("IC2:blockElectric");
        setCreativeTab(IC2.tabIC2);
    }

    @Override
    public TileEntityCharger createNewTileEntity(World world, int meta)
    {
        switch (meta)
        {
            case 1: return new TileEntityChargerMV();
            case 2: return new TileEntityChargerHV();
            case 3: return new TileEntityChargerEV();
            default: return new TileEntityChargerLV();
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side,
                                    float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(!(te instanceof TileEntityCharger))
            return false;

        if(world.isRemote)
            return true;

        player.openGui(ChargerMod.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        TextureMap textureMap = (TextureMap)iconRegister;
        String[] names = {"BatBox","CESU","MFE","MFSU"};
        for(int ni=0; ni < names.length; ni++)
        {
            String name = names[ni];
            for(int active = 0; active < 2; active++)
                for(int side = 0; side < 6; side++)
                {
                    int subIndex = active * 6 + side;
                    icons[ni][subIndex] = textureMap.getTextureExtry("ic2:wiring/block"+name+":"+subIndex);
                }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < icons.length; i++)
            list.add(new ItemStack(item, 1, i));
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
