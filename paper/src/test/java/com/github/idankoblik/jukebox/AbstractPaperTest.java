package com.github.idankoblik.jukebox;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;

public abstract class AbstractPaperTest {

    protected Plugin plugin;
    protected ServerMock server;
    protected BukkitAudiences bukkitAudiences;

    protected void setUp() throws Exception {
        this.server = MockBukkit.mock();
        this.plugin = MockBukkit.createMockPlugin();

        this.bukkitAudiences = BukkitAudiences.create(plugin);
    }

    protected void tearDown() {
        MockBukkit.unmock();
        this.bukkitAudiences.close();
    }


}
