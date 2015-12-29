package br.com.gamemods.ic2.charger.charger;

public class TileEntityChargerEV extends TileEntityCharger
{
    public TileEntityChargerEV()
    {
        super(4, 2048, 40000000);
    }

    @Override
    public String getInventoryName()
    {
        return "MFSU";
    }
}
