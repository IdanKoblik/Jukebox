package com.github.idankoblik.commands;

import com.github.idankoblik.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayNBSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /playnbs <filename>");
            return false;
        }

        Player player = (Player) sender;
        String fileName = args[0];

        JukeboxPluginLoader instance = JukeboxPluginLoader.getInstance();

        try {
            NBSSong nbsSong = new NBSParser().readNBS(new File(instance.getDataFolder(), fileName));
            LoopableSong song = new LoopableSong(nbsSong, instance, NoteInstrument.HARP.getName(), audience(player));
            song.setLoop(true);
            song.playSong(100.0f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private Audience audience(Object object) throws Exception {
        BukkitAudiences bukkitAudiences = JukeboxPluginLoader.getInstance().getBukkitAudiences();

        if (object instanceof Audience audience)
            return audience;
        else if (object instanceof Player player)
            return bukkitAudiences.player(player);
        else if (object instanceof CommandSender sender)
            return bukkitAudiences.sender(sender);

        throw new Exception("Unsupported audience object");
    }
}
