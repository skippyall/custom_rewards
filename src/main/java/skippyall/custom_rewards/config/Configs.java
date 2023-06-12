package skippyall.custom_rewards.config;

import skippyall.custom_rewards.Freundesserver;

import java.io.File;

public class Configs {
    public static void initConfigs(){
        File configFolder= Freundesserver.plugin.getDataFolder();
        if(!configFolder.exists()) {
            configFolder.mkdir();
        }
        TextConfig.initConfig();
        LuckyBlockConfig.initConfig();
    }
}
