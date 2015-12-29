package br.com.gamemods.ic2.charger.proxy;

import br.com.gamemods.ic2.charger.charger.BlockCharger;
import br.com.gamemods.ic2.charger.charger.TileEntityChargerLV;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public BlockCharger blockCharger;

    public void registerBlocks()
    {
        GameRegistry.registerBlock(blockCharger = new BlockCharger(), "charger");
    }

    public void registerTiles()
    {
        GameRegistry.registerTileEntity(TileEntityChargerLV.class, "ic2charger:LV-Charger");
    }
}
