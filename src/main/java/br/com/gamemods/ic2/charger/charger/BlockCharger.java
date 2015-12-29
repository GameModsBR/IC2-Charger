package br.com.gamemods.ic2.charger.charger;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockCharger extends BlockContainer
{
    public BlockCharger()
    {
        super(Material.iron);
        setHardness(1.5f);
        setStepSound(soundTypeMetal);
    }

    @Override
    public TileEntityCharger createNewTileEntity(World world, int meta)
    {
        switch (meta)
        {
            default: return new TileEntityChargerLV();
        }
    }
}
