package net.eiusus.dailyRewardsPlugin;

import net.eiusus.dailyRewardsPlugin.command.RewardCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DailyRewardsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("DailyRewardPlugin enabled");

        Objects.requireNonNull(getCommand("reward")).setExecutor(new RewardCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("DailyRewardPlugin disabled");
    }
}
