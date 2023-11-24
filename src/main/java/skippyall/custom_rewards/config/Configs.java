package skippyall.custom_rewards.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import skippyall.custom_rewards.CustomRewards;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Configs {
    private static ArrayList<Config> configs=new ArrayList<>(Arrays.<Config>asList(new LuckyBlockConfig(), new CoinConfig(), new TextConfig()));

    /**
     * Initializes all configs of this plugin.
     */
    public static void initConfigs() {
        File configFolder= CustomRewards.plugin.getDataFolder();
        if(!configFolder.exists()) {
            configFolder.mkdir();
        }
        for(Config config:configs){
            File configFile = new File(config.getFileName());
            FileConfiguration fileconfig = YamlConfiguration.loadConfiguration(configFile);
            if(!configFile.exists()) {
                fileconfig.options().copyDefaults(true);
                fileconfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(CustomRewards.plugin.getResource("lucky_blocks.yml"))));
                try {
                    fileconfig.save(configFile);
                } catch (IOException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    CustomRewards.plugin.getLogger().warning("Failed to save config " + config.getFileName());
                    CustomRewards.plugin.getLogger().warning(sw.toString());
                }
            }
        }
    }
}
