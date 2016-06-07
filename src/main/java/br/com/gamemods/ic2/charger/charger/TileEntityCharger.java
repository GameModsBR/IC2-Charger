package br.com.gamemods.ic2.charger.charger;

import br.com.gamemods.ic2.charger.ChargerMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityCharger extends TileEntityInventory implements IEnergySink
{
    public final InvSlotCharge chargeSlot;
    public final InvSlotDischarge dischargeSlot;

    public double energy;
    public final double maxStorage;
    private double demand;

    public final int tier;
    public final int voltage;

    TileEntityCharger(int tier, int output, double maxStorage)
    {
        this.maxStorage = maxStorage;
        chargeSlot = new InvSlotCharge(this, 0, tier);
        dischargeSlot = new InvSlotDischarge(this, 1, InvSlot.Access.IO, tier, InvSlot.InvSide.BOTTOM);
        this.tier = tier;
        voltage = output;
        demand = maxStorage - energy;
    }

    @Override
    protected void updateEntityServer()
    {
        boolean markDirty = false;
        double energyBefore = energy;
        if(energy >= 1 && !chargeSlot.isEmpty())
        {
            double decrement = chargeSlot.charge(energy);
            energy -= decrement;
            if(decrement > 0)
                markDirty = true;
        }

        if(!dischargeSlot.isEmpty() && energy < maxStorage)
        {
            double increment = dischargeSlot.discharge(maxStorage - energy, false);
            energy += increment;
            if(increment > 0)
                markDirty = true;
        }

        if(markDirty)
        {
            markDirty();
            if(energyBefore != energy)
                sendEnergyUpdate();
        }
    }

    @Override
    public void markDirty()
    {
        demand = maxStorage - energy;
        super.markDirty();
    }

    @Override
    public void onLoaded()
    {
        super.onLoaded();
        if(!worldObj.isRemote)
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
    }

    @Override
    public void onUnloaded()
    {
        super.onUnloaded();
        if(!worldObj.isRemote)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection)
    {
        return true;
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage)
    {
        if(energy >= maxStorage)
            return amount;

        double increment = Math.max(Math.min(maxStorage - energy, amount), 0);
        energy += increment;
        if(increment > 0)
        {
            markDirty();
            if(!worldObj.isRemote)
                sendEnergyUpdate();
        }
        return amount - increment;
    }

    public void sendEnergyUpdate()
    {
        ChargerMod.network.sendToAllAround(new ChargerEnergyMessage(this),
                new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 10)
        );
    }

    @Override
    public int getSinkTier()
    {
        return tier;
    }

    @Override
    public double getDemandedEnergy()
    {
        return demand;
    }

    public float getChargeLevel()
    {
        float ret = (float)this.energy / (float)this.maxStorage;
        if(ret > 1.0F)
            ret = 1.0F;

        return ret;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
    {
        ItemStack ret = super.getWrenchDrop(entityPlayer);
        float energyRetainedInStorageBlockDrops = ConfigUtil.getFloat(MainConfig.get(), "balance/energyRetainedInStorageBlockDrops");
        if(energyRetainedInStorageBlockDrops > 0.0F)
        {
            double energyRetained = energy * energyRetainedInStorageBlockDrops;
            if(energyRetained <= 0)
                return ret;

            NBTTagCompound nbttagcompound = StackUtil.getOrCreateNbtData(ret);
            nbttagcompound.setDouble("energy", this.energy * energyRetainedInStorageBlockDrops);
        }

        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        energy = Math.max(nbtTagCompound.getDouble("energy"), 0);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setDouble("energy", energy);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }
}
