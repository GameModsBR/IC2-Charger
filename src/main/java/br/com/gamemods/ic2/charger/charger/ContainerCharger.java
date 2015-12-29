package br.com.gamemods.ic2.charger.charger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCharger extends Container
{
    private TileEntityCharger tileEntity;

    public ContainerCharger(InventoryPlayer inventoryPlayer, TileEntityCharger charger)
    {
        this.tileEntity = charger;



        bindPlayerInventory(inventoryPlayer);
    }

    void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int x = 8, y = 114;
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlotToContainer(new Slot(
                        inventoryPlayer,        // Inventory
                        col + (row * 9) + 9,    // Slot
                        x + (col * 18),         // X
                        y + row * 18)           // Y
                );

        for (int slot = 0; slot < 9; slot++)
            addSlotToContainer(new Slot(inventoryPlayer, slot, x + slot * 18, y + 3*18 + 4));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
