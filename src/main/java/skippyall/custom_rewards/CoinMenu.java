package skippyall.custom_rewards;

import org.bukkit.inventory.meta.ItemMeta;
import skippyall.custom_rewards.config.LuckyBlockConfig;
import skippyall.custom_rewards.config.TextConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CoinMenu implements Listener {
    public static Inventory inventory;

    public static void init() {
        List<ItemStack> list= LuckyBlockConfig.getLuckyBlocks();
        ItemStack stack=new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta=stack.getItemMeta();
        meta.setDisplayName("X");
        stack.setItemMeta(meta);
        for(int i=list.size();i<=7;i++){

            list.add(stack.clone());
        }
        list.add(new ItemStack(Material.SPRUCE_DOOR));

        inventory=Bukkit.createInventory(null,9, TextConfig.getText("shop.title"));
        inventory.setContents(list.toArray(new ItemStack[list.size()]));
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e){
        if(e.getItem()!=null &&((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
            if (e.getItem().isSimilar(LuckyBlockConfig.getCoin())) {
                e.getPlayer().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getClickedInventory()!=null) {
            if (e.getClickedInventory() == inventory) {
                if (e.isLeftClick() && e.getCurrentItem() != null) {
                    Inventory playerInventory = e.getWhoClicked().getInventory();
                    Material itemType = e.getCurrentItem().getType();
                    if (itemType == Material.CLOCK && playerInventory.containsAtLeast(LuckyBlockConfig.getCoin(), 1)) {
                        playerInventory.addItem(e.getCurrentItem());
                        ItemStack[] contents = playerInventory.getContents();
                        for (ItemStack stack : contents) {
                            if ((stack != null) && stack.isSimilar(LuckyBlockConfig.getCoin())) {
                                stack.setAmount(stack.getAmount() - 1);
                                playerInventory.setContents(contents);
                                break;
                            }
                        }
                    } else if (itemType == Material.SPRUCE_DOOR) {
                        e.getWhoClicked().closeInventory();
                    }
                }
                e.setCancelled(true);
            }
            if (e.getView().getTopInventory() == inventory && e.isShiftClick()){
                e.setCancelled(true);
            }
        }
    }
}
