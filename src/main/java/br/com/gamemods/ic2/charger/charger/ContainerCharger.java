package br.com.gamemods.ic2.charger.charger;

import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCharger extends Container
{

    public ContainerCharger(InventoryPlayer inventoryPlayer, TileEntityCharger charger)
    {
        for(int col = 0; col < 4; col++)
            addSlotToContainer(new SlotArmor(inventoryPlayer, col, 8 + col * 18, 84));

        addSlotToContainer(new SlotInvSlot(charger.chargeSlot, 0, 56, 17));
        addSlotToContainer(new SlotInvSlot(charger.dischargeSlot, 0, 56, 53));

        bindPlayerInventory(inventoryPlayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
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
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        int size = 6;
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // merges the item into player inventory since its in the tileEntity
            if (slot < size && slot > 3)
            {
                try
                {
                    boolean found = false;
                    for(int i = 0; i < 4; i++)
                    {
                        SlotArmor armor = (SlotArmor) inventorySlots.get(i);
                        if(armor.isItemValid(stackInSlot) && mergeItemStack(stackInSlot, i, i+1, true))
                        {
                            found = true;
                            break;
                        }
                    }

                    if (!found && !this.mergeItemStack(stackInSlot, size, 42, true))
                        return null;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            // places it into the tileEntity is possible since its in the player
            // inventory
            else
            {
                boolean foundSlot = false;
                for (int i = 4; i < size; i++)
                    if (((Slot) inventorySlots.get(i)).isItemValid(stackInSlot)
                            && this.mergeItemStack(stackInSlot, i, i + 1, false))
                    {
                        foundSlot = true;
                        break;
                    }

                if(!foundSlot)
                    for (int i = 0; i < 4; i++)
                        if (((Slot) inventorySlots.get(i)).isItemValid(stackInSlot)
                                && this.mergeItemStack(stackInSlot, i, i + 1, false))
                        {
                            foundSlot = true;
                            break;
                        }

                if (!foundSlot)
                    return null;
            }

            if (stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();

            if (stackInSlot.stackSize == stack.stackSize)
                return null;

            slotObject.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
