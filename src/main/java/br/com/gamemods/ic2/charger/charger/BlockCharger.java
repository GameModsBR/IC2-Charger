package br.com.gamemods.ic2.charger.charger;

import br.com.gamemods.ic2.charger.ChargerMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        if(world.isRemote || stack.stackTagCompound == null)
            return;

        TileEntity te = world.getTileEntity(x,y,z);
        if(!(te instanceof TileEntityCharger))
            return;

        TileEntityCharger charger = (TileEntityCharger) te;
        charger.energy = Math.max(0, Math.min(stack.stackTagCompound.getDouble("energy"), charger.maxStorage));
        world.addBlockEvent(x,y,z,this,0,(int)charger.energy);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
        TileEntity te = world.getTileEntity(x, y, z);
        if(!(te instanceof TileEntityCharger))
            return ret;
        TileEntityCharger charger = (TileEntityCharger)te;

        int size = charger.getSizeInventory();
        for(int i = 0; i < size; i++)
        {
            ItemStack stack = charger.getStackInSlotOnClosing(i);
            if(stack != null)
                ret.add(stack);
        }

        return ret;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune)
    {
        if(meta == 0 || ConfigUtil.getBool(MainConfig.get(), "balance/ignoreWrenchRequirement"))
            return Item.getItemFromBlock(this);

        return Ic2Items.machine.getItem();
    }

    @Override
    public int damageDropped(int meta)
    {
        if(meta == 0 || ConfigUtil.getBool(MainConfig.get(), "balance/ignoreWrenchRequirement"))
            return meta;

        return Ic2Items.machine.getItemDamage();
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(!(te instanceof TileEntityCharger))
            return 0;
        TileEntityCharger charger = (TileEntityCharger) te;

        return (int)Math.round(Util.map(charger.energy, charger.maxStorage, 15));
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
