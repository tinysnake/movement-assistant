package snake.mcmods.movementassistant.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class MAMovementHandler
{
	private boolean needResetKey = false;

	private boolean isAutoMoving = false;

	private int doubleSneakTicks = 0;

	public boolean getIsWaitingForSeconSneak()
	{
		return doubleSneakTicks > 0;
	}

	private boolean isConstantlySneaking = false;

	public void setIsConstantlySneaking(boolean value)
	{
		isConstantlySneaking = value;
		if (value)
		{
			boolean stopMovingFlag = isAutoMoving || isAutoSprinting;
			if (stopMovingFlag)
				stopMoving();
			isAutoMoving = false;
		}
		else
		{
			resetKeyState();
		}
	}

	public boolean getIsAutoMoving()
	{
		return isAutoMoving;
	}

	public void setIsMoving(boolean value)
	{
		isAutoMoving = value;
		if (value)
		{
			isAutoSprinting = false;
			stopSneaking();
		}
		else
		{
			resetKeyState();
		}
	}

	private boolean isAutoSprinting = false;

	public boolean getIsAutoSprinting()
	{
		return isAutoSprinting;
	}

	public void setIsSprinting(boolean value)
	{
		isAutoSprinting = value;
		if (value)
		{
			isAutoMoving = false;
			stopSneaking();
		}
		else
		{
			resetKeyState();
		}
	}

	public void updateMovmentOnPlayerTick()
	{
		Minecraft mc = Minecraft.getMinecraft();

		EntityClientPlayerMP p = mc.thePlayer;

		if (doubleSneakTicks > 0)
			doubleSneakTicks--;
		if (mc.gameSettings.keyBindSneak.isPressed())
		{
			if (getIsWaitingForSeconSneak())
			{
				setIsConstantlySneaking(true);
			}
			else if (isConstantlySneaking)
			{
				isConstantlySneaking = false;
				mc.gameSettings.keyBindSneak.pressed=false;
			}
			else
			{
				startDoubleSneakTimer();
			}
		}

		if (isAutoMoving || isAutoSprinting)
		{
			if (mc.gameSettings.keyBindBack.isPressed())
			{
				stopMoving();
				return;
			}
			mc.gameSettings.keyBindForward.pressed = true;
			if (isAutoSprinting)
				p.setSprinting(true);
		}
		else if (isConstantlySneaking)
		{
			mc.gameSettings.keyBindSneak.pressed = true;
		}

		if (mc.currentScreen != null)
		{
			if (p.isInWater())
			{
				mc.gameSettings.keyBindJump.pressed = true;
			}
			else if (p.isOnLadder())
			{
				mc.gameSettings.keyBindSneak.pressed = true;
			}
			needResetKey = true;
		}
		else if (needResetKey == true)
		{
			resetKeyState();
			needResetKey = false;
		}
	}

	public void resetKeyState()
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.gameSettings.keyBindForward.pressed = false;
		mc.gameSettings.keyBindJump.pressed = false;
		mc.gameSettings.keyBindSneak.pressed = false;
	}

	public void stopMoving()
	{
		setIsMoving(false);
		setIsSprinting(false);
	}
	
	public void stopSneaking()
	{
		setIsConstantlySneaking(false);
	}

	public void startDoubleSneakTimer()
	{
		doubleSneakTicks = 7;
	}
}
