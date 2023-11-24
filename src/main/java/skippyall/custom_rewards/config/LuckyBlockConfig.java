package skippyall.custom_rewards.config;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import skippyall.custom_rewards.CustomRewards;

import java.io.File;
import java.util.*;

public class LuckyBlockConfig implements Config{
    private static File configFile = new File(CustomRewards.plugin.getDataFolder(),"lucky_blocks.yml");
    private static ArrayList<ItemStack> luckyBlocks=new ArrayList<>();
    private static Random random=new Random();

    public void initConfig(FileConfiguration config){
        generateLuckyBlocks(config);
    }

    @Override
    public String getFileName() {
        return "lucky_blocks.yml";
    }

    public static void generateLuckyBlocks(ConfigurationSection config){
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
    public static List<ItemStack> getLuckyBlocks(){
        ArrayList<ItemStack> list=new ArrayList<>();
        for(ItemStack stack:luckyBlocks){
            list.add(stack.clone());
        }
        return list;
    }

    public static String getRandomAction(ItemStack stack){
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
