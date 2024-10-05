package com.github.idankoblik.jukebox;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class AbstractSpigotTest {

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

    protected Audience audience(Object object) throws Exception {
        if (object instanceof Audience audience)
            return audience;
        else if (object instanceof Player player)
            return bukkitAudiences.player(player);
        else if (object instanceof World world)
            return bukkitAudiences.world(Key.key(world.getName()));
        else if (object instanceof CommandSender sender)
            return bukkitAudiences.sender(sender);

        throw new Exception("Unsupported audience object");
    }
}
