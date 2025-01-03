package com.NaturalDisastersWrapper.core;

import com.NaturalDisastersWrapper.commands.TestCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class NaturalDisastersWrapper extends JavaPlugin {

    private Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Initializing NatDisasters Wrapper...");
        //loadCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Disabling NatDisasters Wrapper");
    }

    //private void loadCommands() {
    //    getCommand("test").setExecutor(new TestCommand());
    //}

    @Override
    public boolean onCommand(org.bukkit.command.@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ndw") && sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 3) {
                player.sendMessage("Usage: /ndw <material> <custom_name> <command>");
                return false;
            }

            // Extract material, custom name, and command
            Material material;
            try {
                material = Material.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage("Invalid material: " + args[0]);
                return false;
            }

            String customName = args[1];
            String cmdToExecute = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

            // Create the one-time-use item
            ItemStack specialItem = new ItemStack(material);
            ItemMeta meta = specialItem.getItemMeta();
            meta.setDisplayName(customName);
            meta.setLore(java.util.Arrays.asList("Right-click to execute:", cmdToExecute));
            specialItem.setItemMeta(meta);

            // Store the command in the item's metadata
            specialItem = storeCommandInItem(specialItem, cmdToExecute);

            // Give the item to the player
            player.getInventory().addItem(specialItem);
            player.sendMessage("You have received the one-time-use item named '" + customName + "'!");
            return true;
        }
        return false;
    }

    /**
     * Event listener for right-clicking with the one-time-use item.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            // Retrieve the stored command
            String command = retrieveCommandFromItem(item);

            if (command != null && !command.isEmpty()) {
                // Execute the command as the player
                player.performCommand(command);

                // Remove the item from the player's inventory
                item.setAmount(item.getAmount() - 1);
                player.sendMessage("The one-time-use item '" + item.getItemMeta().getDisplayName() + "' has been used!");
            }
        }
    }

    /**
     * Stores a command in an item's persistent metadata.
     */
    private ItemStack storeCommandInItem(ItemStack item, String command) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new org.bukkit.NamespacedKey(this, "command"),
                org.bukkit.persistence.PersistentDataType.STRING,
                command);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Retrieves a stored command from an item's metadata.
     */
    private String retrieveCommandFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(new org.bukkit.NamespacedKey(this, "command"),
                org.bukkit.persistence.PersistentDataType.STRING);
    }
}
