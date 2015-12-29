package br.com.gamemods.ic2.charger.charger;

public class TileEntityChargerHV extends TileEntityCharger
{
    public TileEntityChargerHV()
    {
        super(3, 512, 4000000);
    }

    @Override
    public String getInventoryName()
    {
        return "MFE";
    }
}
