package br.com.gamemods.ic2.charger.charger;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class ChargerEnergyMessage implements IMessage, IMessageHandler<ChargerEnergyMessage, IMessage>
{
    private int x, y, z, energy;

    public ChargerEnergyMessage()
    {}

    public ChargerEnergyMessage(TileEntityCharger charger)
    {
        x = charger.xCoord;
        y = charger.yCoord;
        z = charger.zCoord;
        energy = (int)charger.energy;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(energy);
    }

    @Override
    public IMessage onMessage(ChargerEnergyMessage message, MessageContext ctx)
    {
        TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
        if(!(te instanceof TileEntityCharger))
            return null;

        ((TileEntityCharger) te).energy = message.energy;
        return null;
    }
}
