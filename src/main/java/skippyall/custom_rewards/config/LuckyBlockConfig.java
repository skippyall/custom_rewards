package skippyall.custom_rewards.config;

import org.jetbrains.annotations.NotNull;
import skippyall.custom_rewards.CustomRewards;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class LuckyBlockConfig {
    private static File configFile = new File(CustomRewards.plugin.getDataFolder(),"lucky_blocks.yml");

    private static ArrayList<ItemStack> luckyBlocks=new ArrayList<>();
    private static ItemStack coin;


    private static Random random=new Random();

    public static void initConfig(){
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            if(!configFile.exists()) {
                config.options().copyDefaults(true);
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(CustomRewards.plugin.getResource("lucky_blocks.yml"))));
                config.save(configFile);
            }
            generateLuckyBlocks(config.getConfigurationSection("luckyBlocks"));
            generateCoin(config.getConfigurationSection("coins"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void generateLuckyBlocks(@NotNull ConfigurationSection config){
        luckyBlocks.clear();
        for(String key:config.getKeys(false)){
            ConfigurationSection luckyBlockConfig=config.getConfigurationSection(key);

            ItemStack stack = new ItemStack(Material.CLOCK);
            ItemMeta itemMeta = stack.getItemMeta();

            itemMeta.getPersistentDataContainer().set(new NamespacedKey(CustomRewards.plugin,"id"), PersistentDataType.STRING,key);
            itemMeta.setCustomModelData(luckyBlockConfig.getInt("customModelData"));
            itemMeta.setDisplayName(luckyBlockConfig.getString("name"));

            String[] lore=luckyBlockConfig.getString("tooltip").split("\n");
            itemMeta.setLore(new ArrayList<String>(Arrays.asList(lore)));

            stack.setItemMeta(itemMeta);
            luckyBlocks.add(stack);
        }
    }
    public static @NotNull List<ItemStack> getLuckyBlocks(){
        ArrayList<ItemStack> list=new ArrayList<>();
        for(ItemStack stack:luckyBlocks){
            list.add(stack.clone());
        }
        return list;
    }

    public static void generateCoin(@NotNull ConfigurationSection config){
        coin = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = coin.getItemMeta();

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(CustomRewards.plugin,"id"), PersistentDataType.STRING,"coin");
        itemMeta.setCustomModelData(config.getInt("customModelData"));
        itemMeta.setDisplayName(config.getString("name"));

        String[] lore=config.getString("tooltip").split("\n");
        itemMeta.setLore(new ArrayList<String>(Arrays.asList(lore)));

        coin.setItemMeta(itemMeta);
    }
    public static @NotNull ItemStack getCoin(){
        return coin.clone();
    }

    public static String getRandomAction(@NotNull ItemStack stack){
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        String key=stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CustomRewards.plugin, "id"),PersistentDataType.STRING);
        ConfigurationSection luckyBlockConfig=config.getConfigurationSection("luckyBlocks").getConfigurationSection(key);

        Map<String,Object> chancesMap=luckyBlockConfig.getConfigurationSection("chances").getValues(false);
        Collection<Object> chances=chancesMap.values();
        double sum=0;
        for(Object o:chances){
            sum+=(Double) o;
        }

        double category=random.nextFloat()*sum;
        double currentCategory=0;
        String categoryKey="";
        for(Map.Entry<String,Object> entry:chancesMap.entrySet()){
            currentCategory+=(Double) entry.getValue();
            if(category<currentCategory){
                categoryKey= entry.getKey();
                break;
            }
        }

        List<String> commandList=config.getConfigurationSection("loottables").getStringList(categoryKey);
        return commandList.get(random.nextInt(commandList.size()));
    }
}
