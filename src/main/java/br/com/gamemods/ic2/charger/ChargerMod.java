package br.com.gamemods.ic2.charger;

import br.com.gamemods.ic2.charger.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "IC2Charger", name = "IC2 Charger", version = "1.0.0",
    dependencies = "required-after:IC2")
public class ChargerMod
{
    @SidedProxy(clientSide = "br.com.gamemods.ic2.charger.proxy.CommonProxy",
                serverSide = "br.com.gamemods.ic2.charger.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Mod.Instance
    public static ChargerMod instance;

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
    }
}
