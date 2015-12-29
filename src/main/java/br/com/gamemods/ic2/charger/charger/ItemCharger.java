package br.com.gamemods.ic2.charger.charger;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCharger extends ItemBlock
{
    public ItemCharger(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        switch (stack.getItemDamage())
        {
            case 0: return getUnlocalizedName()+".lv";
            case 1: return getUnlocalizedName()+".mv";
            case 2: return getUnlocalizedName()+".hv";
            case 3: return getUnlocalizedName()+".ev";
            default:return getUnlocalizedName();
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
