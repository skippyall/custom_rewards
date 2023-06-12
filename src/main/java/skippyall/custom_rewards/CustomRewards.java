package skippyall.custom_rewards;

import skippyall.custom_rewards.config.Configs;
import skippyall.custom_rewards.config.TextConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomRewards extends JavaPlugin {
    public static CustomRewards plugin;
    @Override
    public void onEnable() {
        plugin=this;
        plugin.getCommand("reward").setExecutor(new RewardCommand());

        Configs.initConfigs();
        CoinMenu.init();
        Bukkit.getPluginManager().registerEvents(new CoinMenu(), plugin);
        Bukkit.getPluginManager().registerEvents(new LuckyBlocks(),plugin);
        plugin.getLogger().info(TextConfig.getText("misc.start"));
    }

    @Override
    public void onDisable() {
    }
}
