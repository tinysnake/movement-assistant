package snake.mcmods.movementassistant.handlers;

import snake.mcmods.movementassistant.MovementAssistant;
import snake.mcmods.movementassistant.config.Lang;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeHooks;

public class MAAutoFollowHandler
{
    public final static int MIN_DIS_SQ = 6;
    public final static int MAX_DIS_SQ = 45;
    public final static float MV_SPD = 0.22F;
    public final static double JUMP_SPD = 0.41999998688697815D;

    private boolean isFollowing;
    private int jumpTicks = 0;

    public boolean getIsFollowing()
    {
        return isFollowing;
    }

    private EntityLiving followingEntity;

    public void setFollowingEntity(EntityLiving value)
    {
        if (value == null || value == followingEntity || !getFollowAny(value))
            clearFollowingEntity();
        else
        {
            followingEntity = value;
            isFollowing = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(Lang.getLocalizedStr(Lang.MSG_FOLLOW)
                    + followingEntity.getEntityName());
        }
    }

    private boolean getFollowAny(EntityLiving entity)
    {
        return MovementAssistant.ma.config.getFollowAny() || entity instanceof EntityPlayer;
    }

    public void updateAutoFollow()
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.keyBindForward.isPressed() || mc.gameSettings.keyBindBack.isPressed()
                || mc.gameSettings.keyBindLeft.isPressed()
                || mc.gameSettings.keyBindRight.isPressed()
                || mc.gameSettings.keyBindJump.isPressed()
                || mc.gameSettings.keyBindSneak.isPressed())
        {
            setFollowingEntity(null);
            return;
        }

        if (followingEntity != null)
        {
            EntityPlayer player = mc.thePlayer;
            if (player.isDead || followingEntity.isDead
                    || followingEntity.dimension != player.dimension)
            {
                clearFollowingEntity();
            } else if (followingEntity.isEntityAlive())
            {
                followTargetEntity();
            } else
            {
                clearFollowingEntity();
            }
        }
    }

    private void followTargetEntity()
    {
        if (this.jumpTicks > 0)
            this.jumpTicks--;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        double distance = followingEntity.getDistanceSqToEntity(player);
        if (distance > MIN_DIS_SQ && distance < MAX_DIS_SQ)
        {
            double px = followingEntity.posX - followingEntity.lastTickPosX;
            double pz = followingEntity.posZ - followingEntity.lastTickPosZ;
            float mvspd = (float) MathHelper.sqrt_double(px * px + pz * pz);
            double angle = Math.atan2(followingEntity.posZ - player.posZ, followingEntity.posX
                    - player.posX);
            if (mvspd < MV_SPD)
                mvspd = MV_SPD;
            // Console.println(player.motionX + ", " + player.motionY + ", " +
            // player.motionZ);
            float tx = mvspd * MathHelper.cos((float) angle);
            float tz = mvspd * MathHelper.sin((float) angle);
            player.setVelocity(tx, player.motionY, tz);
        }
        setPlayerJump(player);

        // Console.println("x: " + followingEntity.motionX + "y: " +
        // followingEntity.motionY + "z: " + followingEntity.motionZ);
    }

    private void setPlayerJump(EntityPlayer p)
    {
        if (p.isInWater() || p.handleLavaMovement())
        {
            if (p.isCollidedHorizontally
                    && p.isOffsetPositionInLiquid(p.motionX, p.motionY + 0.6000000238418579D,
                            p.motionZ))
            {
                p.motionY = 0.30000001192092896D;
            } else
            {
                p.motionY = 0.03999999910593033D;
            }
            p.motionX *= .5F;
            p.motionZ *= .5F;

            this.jumpTicks = 10;
        } else if ((p.isCollidedHorizontally && p.onGround))
        {
            if (p.onGround && this.jumpTicks == 0)
            {
                p.motionY = 0.41999998688697815D;

                if (p.isPotionActive(Potion.jump))
                {
                    p.motionY += (double) ((float) (p.getActivePotionEffect(Potion.jump)
                            .getAmplifier() + 1) * 0.1F);
                }

                if (p.isSprinting())
                {
                    float f = p.rotationYaw * 0.017453292F;
                    p.motionX -= (double) (MathHelper.sin(f) * 0.2F);
                    p.motionZ += (double) (MathHelper.cos(f) * 0.2F);
                }

                p.isAirBorne = true;
                ForgeHooks.onLivingJump(p);

                this.jumpTicks = 10;
            }

        }
    }

    private void clearFollowingEntity()
    {
        if (followingEntity != null)
            Minecraft.getMinecraft().thePlayer.addChatMessage(Lang.getLocalizedStr(Lang.MSG_UNFOLLOW)
                    + followingEntity.getEntityName());
        followingEntity = null;
        isFollowing = true;
    }
}
