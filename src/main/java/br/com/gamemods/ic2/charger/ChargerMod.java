package br.com.gamemods.ic2.charger;

import br.com.gamemods.ic2.charger.proxy.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ic2.core.IC2;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "IC2Charger", name = "IC2 Charger", version = "1.0.1",
    dependencies = "required-after:IC2")
public class ChargerMod
{
    @SidedProxy(clientSide = "br.com.gamemods.ic2.charger.proxy.CommonProxy",
                serverSide = "br.com.gamemods.ic2.charger.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Mod.Instance
    public static ChargerMod instance;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        proxy.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        proxy.registerBlocks();
        proxy.registerTiles();
        proxy.registerNetwork(this);
        proxy.registerRecipes();
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onCraft(PlayerEvent.ItemCraftedEvent event)
    {
        ItemStack stack = event.crafting;
        if(stack.isItemEqual(proxy.batBox))
            IC2.achievements.issueAchievement(event.player, "buildBatBox");
        else if(stack.isItemEqual(proxy.mfe))
            IC2.achievements.issueAchievement(event.player, "buildMFE");
        else if(stack.isItemEqual(proxy.mfe))
            IC2.achievements.issueAchievement(event.player, "buildMFS");
    }
}
