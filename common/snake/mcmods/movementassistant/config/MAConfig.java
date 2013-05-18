package snake.mcmods.movementassistant.config;

import java.io.File;

import snake.mcmods.movementassistant.constants.GlobalConsts;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class MAConfig extends Configuration
{
    public final static String CATEGORY_OTHERS = "others";
    public final static String KEY_VERSION = "version";
    public final static String KEY_FOLLOW_ANY = "followAny";
    
    public MAConfig(File file)
    {
        super(file);
        
        Property versionProp = getVersionProp();
        if(versionProp.value=="")
        {
            firstGen();
            save();
        }
        else if(versionProp.value != GlobalConsts.VERSION)
        {
            generateConfiguration();
            save();
        }
    }
    
    public Property getVersionProp()
    {
        return get(CATEGORY_OTHERS, KEY_VERSION, "");
    }
    
    public String getVersion()
    {
        return getVersionProp().value;
    }
    
    public void setVersion(String value)
    {
        getVersionProp().value = value;
    }
    
    public Property getFollowAnyProp()
    {
        return get(CATEGORY_GENERAL, KEY_FOLLOW_ANY, false);
    }
    
    public boolean getFollowAny()
    {
        return getFollowAnyProp().getBoolean(false);
    }
    
    public void setFollowAny(boolean value)
    {
        getFollowAnyProp().value = String.valueOf(value);
    }
    
    protected void firstGen()
    {
        setFollowAny(false);
        setVersion(GlobalConsts.VERSION);
    }
    
    protected void generateConfiguration()
    {
        setVersion(GlobalConsts.VERSION);
    }
}
