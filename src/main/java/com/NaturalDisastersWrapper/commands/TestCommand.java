package com.NaturalDisastersWrapper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("test")){
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage("hi from nat disasters plugin");
            } else {
                sender.sendMessage("Error: must be player");
            }
        }
        return false;
    }
}
