package snake.mcmods.movementassistant.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumMovingObjectType;

import org.lwjgl.input.Keyboard;

import snake.mcmods.movementassistant.MovementAssistant;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class MAKeyHandler extends KeyHandler
{
    private final static KeyBinding AUTO_WALK_KEY = new KeyBinding(
            "Auto Walk Key", Keyboard.KEY_C);
    private final static KeyBinding AUTO_SPRINT_KEY = new KeyBinding(
            "Auto Sprint Key", Keyboard.KEY_V);
    private final static KeyBinding AUTO_FOLLOW_KEY = new KeyBinding(
            "Auto Follow Key", Keyboard.KEY_F);
    private final static KeyBinding[] KBS =
    { AUTO_WALK_KEY, AUTO_SPRINT_KEY, AUTO_FOLLOW_KEY};
    private final static boolean[] RPT = new boolean[]
    { false, false, false };

    public MAKeyHandler()
    {
        super(KBS, RPT);
    }

    private EnumSet<TickType> tickType = EnumSet.of(TickType.CLIENT);

    @Override
    public String getLabel() {
        return "CMKeyHandler";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb,
            boolean tickEnd, boolean isRepeat) {
        if (kb.isPressed())
        {
            if (kb.keyCode == AUTO_WALK_KEY.keyCode)
            {
                MovementAssistant.movHandler
                        .setIsMoving(!MovementAssistant.movHandler
                                .getIsAutoMoving());
                MovementAssistant.followHandler.setFollowingEntity(null);
            }
            else if (kb.keyCode == AUTO_SPRINT_KEY.keyCode)
            {
                MovementAssistant.movHandler
                        .setIsSprinting(!MovementAssistant.movHandler
                                .getIsAutoSprinting());
                MovementAssistant.followHandler.setFollowingEntity(null);
            }
            else if (kb.keyCode == AUTO_FOLLOW_KEY.keyCode)
            {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.objectMouseOver != null
                        && mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY
                        && mc.objectMouseOver.entityHit instanceof EntityLiving)
                {
                    MovementAssistant.followHandler
                            .setFollowingEntity((EntityLiving) mc.objectMouseOver.entityHit);
                }
                else
                {
                    MovementAssistant.followHandler
                            .setFollowingEntity(null);
                }
                MovementAssistant.movHandler.stopMoving();
                MovementAssistant.movHandler.stopSneaking();
            }
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return tickType;
    }

}
