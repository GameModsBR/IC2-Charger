package br.com.gamemods.ic2.charger.proxy;

import br.com.gamemods.ic2.charger.ChargerMod;
import br.com.gamemods.ic2.charger.GuiHandler;
import br.com.gamemods.ic2.charger.charger.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.AdvRecipe;
import ic2.core.Ic2Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.Iterator;

public class CommonProxy
{
    public BlockCharger blockCharger;
    public byte configs;

    public void registerBlocks()
    {
        GameRegistry.registerBlock(blockCharger = new BlockCharger(), ItemCharger.class, "charger");
    }

    public void registerTiles()
    {
        GameRegistry.registerTileEntity(TileEntityChargerLV.class, "ic2charger:LV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerMV.class, "ic2charger:MV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerHV.class, "ic2charger:HV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerEV.class, "ic2charger:EV-Charger");
    }

    public void registerNetwork(ChargerMod mod)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, new GuiHandler());
    }

    public void registerRecipes()
    {
        boolean replace = (configs & 1) > 0;
        boolean remove = (configs & 1<<1) > 0;
        if(configs > 0)
        {
            @SuppressWarnings("unchecked")
            Iterator<IRecipe> recipes = CraftingManager.getInstance().getRecipeList().iterator();
            while (recipes.hasNext())
            {
                IRecipe recipe = recipes.next();
                if(recipe instanceof AdvRecipe)
                {
                    AdvRecipe advRecipe = (AdvRecipe) recipe;
                    ItemStack recipeOutput = advRecipe.getRecipeOutput();
                    if(recipeOutput.getItem() == Ic2Items.batBox.getItem())
                    {
                        switch (recipeOutput.getItemDamage())
                        {
                            case 0:case 1:case 2:case 7:
                                if(replace)
                                    recipeOutput.func_150996_a(Item.getItemFromBlock(blockCharger));
                            break;
                        }
                    }
                    else if(remove && recipeOutput.getItem() == Ic2Items.ChargepadbatBox.getItem())
                    {
                        recipes.remove();
                    }
                }
            }
        }
    }

    public void loadConfig(Configuration config)
    {
        config.load();
        Property prop = config.get("recipe", "replace-recipes", true);
        prop.comment = "Set to true to replace BatBox, CESU, MFE and MFSU recipes";
        configs |= prop.getBoolean()? 1 : 0;

        prop = config.get("recipe", "disable-chargepads-recipes", true);
        prop.comment = "Set tto true to disable all chargepads recipes";
        configs |= prop.getBoolean()? 1<<1 : 0;

        config.save();
    }
}
