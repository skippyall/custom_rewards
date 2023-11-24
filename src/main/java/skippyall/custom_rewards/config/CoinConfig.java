package skippyall.custom_rewards.config;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import skippyall.custom_rewards.CustomRewards;

import java.util.ArrayList;
import java.util.Arrays;

public class CoinConfig {
    private static ItemStack coin;

    public static void initConfig(){
        generateCoin(config.getConfigurationSection("coins"));
    }
    public static void generateCoin(ConfigurationSection config){
        coin = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = coin.getItemMeta();

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(CustomRewards.plugin,"id"), PersistentDataType.STRING,"coin");
        itemMeta.setCustomModelData(config.getInt("customModelData"));
        itemMeta.setDisplayName(config.getString("name"));

        String[] lore=config.getString("tooltip").split("\n");
        itemMeta.setLore(new ArrayList<String>(Arrays.asList(lore)));

        coin.setItemMeta(itemMeta);
    }
    public static ItemStack getCoin(){
        return coin.clone();
    }
}
