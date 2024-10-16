package com.github.idankoblik.jukebox;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SongTick extends BukkitRunnable {
    private final List<NBSNote> notes;
    private final AbstractSong abstractSong;
    private final CompletableFuture<NBSSong> future;
    private int tick = -1;

    public SongTick(@NotNull List<NBSNote> notes, AbstractSong abstractSong, CompletableFuture<NBSSong> future) {
        this.notes = notes;
        this.abstractSong = abstractSong;
        this.future = future;
    }

    @Override
    public void run() {
        if (notes.isEmpty()) {
            completeSong();
            return;
        }

        if (tick >= notes.size()) {
            if (!abstractSong.toLoop()) {
                abstractSong.song.setState(SongState.ENDED);
                completeSong();
                return;
            }

            tick = 0;
        }

        if (tick >= 0 && tick < notes.size()) {
            NBSNote note = notes.get(tick);
            if (note != null) {
                float pitch = (float) Math.pow(2, (note.getKey() - 45) / 12.0);
                this.abstractSong.playNote(note.getInstrument(), pitch);
            }
        }

        tick++;
    }

    private void completeSong() {
        future.complete(abstractSong.song);
        cancel();
    }
}
