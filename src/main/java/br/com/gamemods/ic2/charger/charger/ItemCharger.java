package br.com.gamemods.ic2.charger.charger;

import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

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
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        @SuppressWarnings("unchecked")
        List<String> info = list;
        int meta = stack.getItemDamage();
        switch(meta)
        {
            case 0:
                info.add(StatCollector.translateToLocalFormatted("ic2.charger.max", 32) + " " + StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " 40k EU ");
                break;
            case 1:
                info.add(StatCollector.translateToLocalFormatted("ic2.charger.max", 128) + " " + StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " 300k EU");
            case 2:
                info.add(StatCollector.translateToLocalFormatted("ic2.charger.max", 512) + " " + StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " 4m EU");
                break;
            case 3:
                info.add(StatCollector.translateToLocalFormatted("ic2.charger.max", 2048) + " " + StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " 40m EU");
                break;
        }

        NBTTagCompound nbttagcompound = StackUtil.getOrCreateNbtData(stack);
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Store") + " " + nbttagcompound.getInteger("energy") + " EU");
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
