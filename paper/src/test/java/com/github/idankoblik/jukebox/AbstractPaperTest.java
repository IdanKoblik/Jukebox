package com.github.idankoblik.jukebox;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.plugin.Plugin;

public abstract class AbstractPaperTest {

    protected Plugin plugin;
    protected ServerMock server;

    protected void setUp() throws Exception {
        this.server = MockBukkit.mock();
        this.plugin = MockBukkit.createMockPlugin();
    }

    protected void tearDown() {
        MockBukkit.unmock();
    }

}
