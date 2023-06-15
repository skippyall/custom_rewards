package skippyall.custom_rewards;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import skippyall.custom_rewards.config.LuckyBlockConfig;
import skippyall.custom_rewards.config.TextConfig;

import java.time.LocalDate;

public class RewardCommand implements CommandExecutor {
    public static final String lastreward="custom_rewards.lastreward";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(TextConfig.getText("reward.invalidSender"));
            return true;
        }

        Player player = (Player) sender;

        PersistentDataContainer container=player.getPersistentDataContainer();
        NamespacedKey key=new NamespacedKey(CustomRewards.plugin,lastreward);
        if(container.has(key,PersistentDataType.LONG)) {
            long time = container.get(key, PersistentDataType.LONG);
            if (time == LocalDate.now().toEpochDay()) {
                player.sendMessage(TextConfig.getText("reward.alreadyTaken"));
                return true;
            }
        }
        container.set(key, PersistentDataType.LONG, LocalDate.now().toEpochDay());

        player.getInventory().addItem(LuckyBlockConfig.getCoin());

        player.sendMessage(TextConfig.getText("reward.success"));

        return true;
    }
}
