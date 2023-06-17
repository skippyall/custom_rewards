package skippyall.custom_rewards;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandExecutor;
import dev.jorel.commandapi.executors.PlayerCommandExecutor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import skippyall.custom_rewards.config.LuckyBlockConfig;
import skippyall.custom_rewards.config.TextConfig;

import java.time.LocalDate;

public class RewardCommand {
    public static void init() {
        PlayerCommandExecutor claimExecutor = (player,args) -> {
            PersistentDataContainer container=player.getPersistentDataContainer();
            NamespacedKey key=new NamespacedKey(CustomRewards.plugin,"custom_rewards.lastreward");
            if(container.has(key, PersistentDataType.LONG)) {
                long time = container.get(key, PersistentDataType.LONG);
                if (time == LocalDate.now().toEpochDay()) {
                    player.sendMessage(TextConfig.getText("reward.claim.alreadyTaken"));
                    return;
                }
            }
            container.set(key, PersistentDataType.LONG, LocalDate.now().toEpochDay());

            player.getInventory().addItem(LuckyBlockConfig.getCoin());

            player.sendMessage(TextConfig.getText("reward.claim.success"));
        };

        CommandExecutor giveExecutor = (sender,args)->{
            Player player = (Player) args.get(0);
            String item = (String) args.get(1);
            int number = (Integer) args.get(2);
            ItemStack stack;
            if(item.equals("coin")) {
                stack = LuckyBlocks.getCoin();
            } else {
                stack = LuckyBlocks.getLuckyBlock(item);
            }
            if (stack != null) {
                stack.setAmount(number);
                player.getInventory().addItem(stack);
            }
        };

        new CommandAPICommand("reward")
            .withSubcommands(new CommandAPICommand("give")
                .withArguments(new PlayerArgument("target"), new StringArgument("item"), new IntegerArgument("number",1))
                .executes(giveExecutor)
                .withPermission("custom_rewards.command.give")
                .withUsage(TextConfig.getText("reward.give.usage"))
            ).withSubcommands(new CommandAPICommand("claim")
                .executesPlayer(claimExecutor)
                .withPermission("custom_rewards.command.claim")
                .withUsage(TextConfig.getText("reward.claim.usage"))
            ).executesPlayer(claimExecutor)
            .withPermission("custom_rewards.command.claim")
            .register();
    }
}
