package skippyall.custom_rewards;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import skippyall.custom_rewards.config.LuckyBlockConfig;
import skippyall.custom_rewards.config.TextConfig;

import java.time.LocalDate;

public class TestCommand {
    public static void init() {
        new CommandAPICommand("reward")
            .withSubcommands(new CommandAPICommand("give")
                .withArguments(new PlayerArgument("target"), new StringArgument("item"), new IntegerArgument("number",1))
                    .executes((sender,args)->{
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
                    })
            ).withSubcommands(new CommandAPICommand("claim").executesPlayer((player,args) -> {
                    PersistentDataContainer container=player.getPersistentDataContainer();
                    NamespacedKey key=new NamespacedKey(CustomRewards.plugin,"custom_rewards.lastreward");
                    if(container.has(key, PersistentDataType.LONG)) {
                        long time = container.get(key, PersistentDataType.LONG);
                        if (time == LocalDate.now().toEpochDay()) {
                            player.sendMessage(TextConfig.getText("reward.alreadyTaken"));
                            return;
                        }
                    }
                    container.set(key, PersistentDataType.LONG, LocalDate.now().toEpochDay());

                    player.getInventory().addItem(LuckyBlockConfig.getCoin());

                    player.sendMessage(TextConfig.getText("reward.success"));
                })
            ).register();
    }
}
