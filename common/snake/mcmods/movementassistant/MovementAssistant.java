package snake.mcmods.movementassistant;

import java.util.logging.Logger;

import snake.mcmods.movementassistant.config.Lang;
import snake.mcmods.movementassistant.config.MAConfig;
import snake.mcmods.movementassistant.constants.GlobalConsts;
import snake.mcmods.movementassistant.handlers.MAAutoFollowHandler;
import snake.mcmods.movementassistant.handlers.MAMovementHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = GlobalConsts.MOD_ID, name = GlobalConsts.MOD_NAME, version = GlobalConsts.VERSION)
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class MovementAssistant
{
    @Instance(GlobalConsts.MOD_ID)
    public static MovementAssistant ma;

    public static MAMovementHandler movHandler = new MAMovementHandler();
    
    public static MAAutoFollowHandler followHandler = new MAAutoFollowHandler();

    @SidedProxy(clientSide = "snake.mcmods.movementassistant.client.ClientProxy", serverSide = "snake.mcmods.movementassistant.CommonProxy")
    public static CommonProxy proxy;

    public MAConfig config;

    public Logger logger;

    @PreInit
    public void preInit(FMLPreInitializationEvent e) {
        logger = Logger.getLogger(GlobalConsts.MOD_NAME);
        config = new MAConfig(e.getSuggestedConfigurationFile());
    }

    @Init
    public void init(FMLInitializationEvent e) {
        Lang.loadLocalizedFiles();
        
        proxy.registerMisc();
        
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent e) {

    }
}
