package snake.mcmods.movementassistant.handlers;

import java.util.EnumSet;

import snake.mcmods.movementassistant.MovementAssistant;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MATickHandler implements ITickHandler
{

    private EnumSet<TickType> tickTypes = EnumSet.of(TickType.PLAYER);

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        MovementAssistant.movHandler.updateMovmentOnPlayerTick();
        MovementAssistant.followHandler.updateAutoFollow();
        
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return tickTypes;
    }

    @Override
    public String getLabel() {
        return "CMTickHandler";
    }

}
