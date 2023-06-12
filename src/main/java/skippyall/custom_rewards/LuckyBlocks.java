package skippyall.custom_rewards;

import skippyall.custom_rewards.config.LuckyBlockConfig;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class LuckyBlocks implements Listener {
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e){
        if(e.getItem()!=null &&((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
            if(e.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Freundesserver.plugin,"id"), PersistentDataType.STRING)){
                if(!e.getItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Freundesserver.plugin,"id"), PersistentDataType.STRING).equals("coin")){
                    String command= LuckyBlockConfig.getRandomAction(e.getItem());
                    if(command.startsWith("/")){
                        command=command.substring(1);
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "execute as "+e.getPlayer().getName()+" at @s run "+command);
                    e.getItem().setAmount(e.getItem().getAmount()-1);
                }
            }
        }
    }
}
