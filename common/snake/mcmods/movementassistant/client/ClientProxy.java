package snake.mcmods.movementassistant.client;

import snake.mcmods.movementassistant.CommonProxy;
import snake.mcmods.movementassistant.handlers.MAKeyHandler;
import snake.mcmods.movementassistant.handlers.MATickHandler;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerMisc()
    {
        TickRegistry.registerTickHandler(new MATickHandler(), Side.CLIENT);
        KeyBindingRegistry.registerKeyBinding(new MAKeyHandler());
    }

}
