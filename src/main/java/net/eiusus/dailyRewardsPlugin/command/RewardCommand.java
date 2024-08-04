package net.eiusus.dailyRewardsPlugin.command;

import net.eiusus.dailyRewardsPlugin.DailyRewardsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RewardCommand implements CommandExecutor {

    private final Map<String, LocalDate> claimedRewards = new HashMap<>();
    private final DailyRewardsPlugin plugin;

    public RewardCommand(DailyRewardsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            plugin.getLogger().warning("You need to be a player to execute this command.");
            return true;
        }

        String playerName = player.getName();
        LocalDate today = LocalDate.now();
        LocalDate lastClaim = claimedRewards.getOrDefault(playerName, null);
        PlayerInventory playerInventory = player.getInventory();

        if (lastClaim == null || !lastClaim.equals(today)) {
            if (playerInventory.firstEmpty() != -1) {
                // Player has an empty slot
                ItemStack reward = new ItemStack(Material.DIAMOND, 1);
                playerInventory.addItem(reward);
                claimedRewards.put(player.getName(), LocalDate.now());
                player.sendMessage(ChatColor.GREEN + "Úspešne si si vybral dennú odmenu");
            } else {
                // Player has no empty slots
                player.sendMessage(ChatColor.RED + "Tvoj inventár je plný!");
            }
        } else {
            // Player has already claimed the daily reward
            player.sendMessage(ChatColor.YELLOW + "Svoju dennú odmenu si si už vybral.");
        }

        return true;
    }
}
