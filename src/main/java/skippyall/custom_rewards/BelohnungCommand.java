package skippyall.custom_rewards;

import skippyall.custom_rewards.config.LuckyBlockConfig;
import skippyall.custom_rewards.config.TextConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.time.LocalDate;

public class BelohnungCommand implements CommandExecutor {
    public static final String lastreward="freundesserver.lastreward";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(TextConfig.getText("reward.invalidSender"));
            return true;
        }

        Player player = (Player) sender;

        for (MetadataValue metadata : player.getMetadata(lastreward)) {
            if (metadata.getOwningPlugin() == Freundesserver.plugin) {
                long time = metadata.asLong();
                if (time == LocalDate.now().toEpochDay()) {
                    player.sendMessage(TextConfig.getText("reward.alreadyTaken"));
                    return true;
                }
            }
        }

        player.setMetadata(lastreward, new FixedMetadataValue(Freundesserver.plugin,LocalDate.now().toEpochDay()));

        player.getInventory().addItem(LuckyBlockConfig.getCoin());

        player.sendMessage(TextConfig.getText("reward.success"));

        return true;
    }
}
