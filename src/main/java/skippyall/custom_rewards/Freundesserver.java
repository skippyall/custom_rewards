package skippyall.custom_rewards;

import skippyall.custom_rewards.config.Configs;
import skippyall.custom_rewards.config.TextConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Freundesserver extends JavaPlugin {
    public static Freundesserver plugin;
    @Override
    public void onEnable() {
        plugin=this;
        plugin.getCommand("belohnung").setExecutor(new BelohnungCommand());

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
