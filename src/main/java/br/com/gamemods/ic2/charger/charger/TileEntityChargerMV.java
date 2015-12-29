package br.com.gamemods.ic2.charger.charger;

public class TileEntityChargerMV extends TileEntityCharger
{
    public TileEntityChargerMV()
    {
        super(2, 128, 300000);
    }

    @Override
    public String getInventoryName()
    {
        return "CESU";
    }
}
