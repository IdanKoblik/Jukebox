package com.github.idankoblik;

import com.github.idankoblik.commands.PlayNBSCommand;
import com.github.idankoblik.manager.InstrumentManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class JukeboxPluginLoader extends JavaPlugin {

    private static JukeboxPluginLoader instance;
    private BukkitAudiences bukkitAudiences;

    @Override
    public void onEnable() {
        instance = this;
        this.bukkitAudiences = BukkitAudiences.create(this);

        Map<Byte, Key> sounds = new HashMap<>();
        sounds.put((byte) 0, Key.key(NoteInstrument.HARP.getName()));
        sounds.put((byte) 1, Key.key(NoteInstrument.BASS.getName()));
        sounds.put((byte) 2, Key.key(NoteInstrument.BASEDRUM.getName()));
        sounds.put((byte) 3, Key.key(NoteInstrument.SNARE.getName()));
        sounds.put((byte) 4, Key.key(NoteInstrument.HAT.getName()));
        sounds.put((byte) 5, Key.key(NoteInstrument.GUITAR.getName()));
        sounds.put((byte) 6, Key.key(NoteInstrument.FLUTE.getName()));
        sounds.put((byte) 7, Key.key(NoteInstrument.BELL.getName()));
        sounds.put((byte) 8, Key.key(NoteInstrument.CHIME.getName()));
        sounds.put((byte) 9, Key.key(NoteInstrument.XYLOPHONE.getName()));
        InstrumentManager.getInstance().loadInstruments(sounds);

        getCommand("playnbs").setExecutor(new PlayNBSCommand());
    }

    @Override
    public void onDisable() {
        bukkitAudiences.close();
    }

    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public static JukeboxPluginLoader getInstance() {
        return instance;
    }

    private Audience audience(Object object) throws Exception {
        if (object instanceof Audience audience)
            return audience;
        else if (object instanceof Player player)
            return bukkitAudiences.player(player);
        else if (object instanceof CommandSender sender)
            return bukkitAudiences.sender(sender);

        throw new Exception("Unsupported audience object");
    }
}
