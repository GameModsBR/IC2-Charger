package br.com.gamemods.ic2.charger;

import br.com.gamemods.ic2.charger.charger.ContainerCharger;
import br.com.gamemods.ic2.charger.charger.GuiCharger;
import br.com.gamemods.ic2.charger.charger.TileEntityCharger;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TileEntityCharger)
            return new ContainerCharger(player.inventory, (TileEntityCharger) te);

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TileEntityCharger)
            return new GuiCharger(player.inventory, (TileEntityCharger) te);

        return null;
    }
}
