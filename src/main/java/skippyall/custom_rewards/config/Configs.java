package skippyall.custom_rewards.config;

import skippyall.custom_rewards.CustomRewards;

import java.io.File;

public class Configs {
    public static void initConfigs(){
        File configFolder= CustomRewards.plugin.getDataFolder();
        if(!configFolder.exists()) {
            configFolder.mkdir();
        }
        TextConfig.initConfig();
        LuckyBlockConfig.initConfig();
    }
}
