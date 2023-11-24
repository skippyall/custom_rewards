package skippyall.custom_rewards.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface Config {
    void initConfig(FileConfiguration config);
    String getFileName();
}
