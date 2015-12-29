package br.com.gamemods.ic2.charger.charger;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCharger extends TileEntity
{
    public double energy;
    public double maxStorage;

    public float getChargeLevel()
    {
        float ret = (float)this.energy / (float)this.maxStorage;
        if(ret > 1.0F)
            ret = 1.0F;

        return ret;
    }
}
