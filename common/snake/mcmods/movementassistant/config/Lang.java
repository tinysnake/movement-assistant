package snake.mcmods.movementassistant.config;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class Lang
{
    public final static String MSG_FOLLOW = "msg.follow";
    public final static String MSG_UNFOLLOW = "msg.unfollow";

    public final static String[] LOCALIZE_FILES =
    { "en_US", "en_UK", "en_AU", "zh_CN" };

    private final static String FILE_DIR = "/mods/MovementAssistant/lang/";

    public static void loadLocalizedFiles()
    {
        for (String f : LOCALIZE_FILES)
        {
            LanguageRegistry.instance().loadLocalization(FILE_DIR + f + ".txt", f, false);
        }
    }
    
    public static String getLocalizedStr(String key)
    {
        String val = LanguageRegistry.instance().getStringLocalization(key);
        if(val==null||val=="")
        {
            val = LanguageRegistry.instance().getStringLocalization(key, "en_US");
        }
        return val;
    }
}
