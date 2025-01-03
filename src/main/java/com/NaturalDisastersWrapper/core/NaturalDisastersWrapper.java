package com.NaturalDisastersWrapper.core;

import com.NaturalDisastersWrapper.commands.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class NaturalDisastersWrapper extends JavaPlugin {

    private Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Initializing NatDisasters Wrapper...");
        loadCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Disabling NatDisasters Wrapper");
    }

    private void loadCommands() {
        getCommand("test").setExecutor(new TestCommand());
    }
}
