package com.github.idankoblik;

import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Represents a song that plays a series of notes.
 */
public class Song extends AbstractSong {

    /**
     * Allow looping a song.
     */
    private boolean loop;

    /**
     * Constructs a Song with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     */
    public Song(NBSSong song, @NotNull String defaultSound, Audience audience) {
        super(song, defaultSound, audience);
    }

    /**
     * Constructs a Song with the given parameters, including a position.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     * @param position     The position where the sound will be played.
     * @param audience     The audience that will hear the sound.
     */
    public Song(NBSSong song, @NotNull String defaultSound, @NotNull Position position, Audience audience) {
        super(song, defaultSound, position, audience);
    }

    /**
     * Play's the nbs song.
     * @param volume The volume at which to play the song.
     * @return Future when the song ends.
     */
    public CompletableFuture<Void> playSong(float volume) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        if (song.notes().isEmpty()) {
            future.complete(null);
            return future;
        }

        playing = true;
        List<NBSNote> notes = song.notes();
        int totalNotes = notes.size();

        for (int i = 0; i < totalNotes; i++) {
            final int index = i;
            long delayInMillis = Math.round(notes.get(i).getTick() * tickLengthInSeconds * 50);

            scheduler.schedule(() -> {
                if (!playing) {
                    future.complete(null);
                    return;
                }

                float pitch = (float) Math.pow(2, (notes.get(index).getKey() - 45) / 12.0);
                playSound(notes.get(index).getInstrument(), pitch, volume);

                if (index == totalNotes - 1) {
                    if (loop)
                        playSong(volume);
                    else
                        future.complete(null);
                }
            }, delayInMillis, TimeUnit.MILLISECONDS);
        }

        return future;
    }

    /**
     * Stop's the nbs song.
     */
    public void stopSong() {
        if (playing)
            scheduler.shutdown();

        loop = false;
        playing = false;
    }

    /**
     * Checking if song loop enabled
     * @return if song loop enabled.
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Enable/Disable song looping
     * @param loop Enable/Disable
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}