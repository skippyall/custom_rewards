package skippyall.custom_rewards.config;

import skippyall.custom_rewards.CustomRewards;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

public class TextConfig {
    private static File configFile=new File(CustomRewards.plugin.getDataFolder(), "texts.yml");
    public static void initConfig(){
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            config.options().copyDefaults(true);
            config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(CustomRewards.plugin.getResource("texts.yml"))));
            config.save(configFile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getText(String path){
        return YamlConfiguration.loadConfiguration(configFile).getString(path);
    }
}
