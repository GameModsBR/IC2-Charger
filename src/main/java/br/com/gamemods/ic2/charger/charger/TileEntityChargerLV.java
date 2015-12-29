package br.com.gamemods.ic2.charger.charger;

public class TileEntityChargerLV extends TileEntityCharger
{
    public TileEntityChargerLV()
    {
        super(1, 32, 40000);
    }

    @Override
    public String getInventoryName()
    {
        return "BatBox";
    }
}
