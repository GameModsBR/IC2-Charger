package br.com.gamemods.ic2.charger.charger;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCharger extends ItemBlock
{
    public ItemCharger(Block block)
    {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        switch (stack.getItemDamage())
        {
            default: return getUnlocalizedName()+".lv";
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
