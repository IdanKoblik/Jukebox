---
title: Song class
editLink: true
---

The `Song` class represents a non-loopable song that plays a series of notes. It extends the `AbstractSong` class and provides functionality to play NBS (Note Block Studio) songs in a Minecraft environment.

> A song can also be played in a specific location you just need to define audiance as world
>

## Class Declaration

```java
public class Song extends AbstractSong {
    // ... (class implementation)
}
```

## Constructors

### Song(NBSSong, Plugin, String, Audience)

Constructs a Song with the given parameters.

```java
public Song(NBSSong song, Plugin plugin, @NotNull String defaultSound, Audience audience)
```

- `song`: The NBS song to be played.
- `plugin`: The plugin instance.
- `defaultSound`: The default sound key.
- `audience`: The audience that will hear the sound.

### Song(NBSSong, Plugin, String, Location, Audience)

Constructs a Song with the given parameters, including a location.

```java
public Song(NBSSong song, Plugin plugin, @NotNull String defaultSound, @NotNull Location location, Audience audience)
```

- `song`: The NBS song to be played.
- `plugin`: The plugin instance.
- `defaultSound`: The default sound key.
- `location`: The location where the sound will be played.
- `audience`: The audience that will hear the sound.

## Methods

### playSong(float volume)

Plays the song at the specified volume.

```java
@Override
public void playSong(float volume)
```

- If the song has no notes, the method returns immediately.
- Sets the `playing` flag to true.
- Iterates through each note in the song:
  - Schedules a `BukkitRunnable` to play each note at the appropriate time.
  - Calculates the pitch based on the note's key.
  - Calls `playSound` with the note's instrument, calculated pitch, and specified volume.
- If `playing` becomes false during iteration, the loop breaks.

### stopSong()

Stops the currently playing song.

```java
@Override
public void stopSong()
```

- Sets the `playing` flag to false if the song is currently playing.

## Usage Example

```java
Plugin myPlugin = // ... obtain plugin instance
NBSSong nbsSong = // ... load NBS song
Audience audience = // ... define audience

Song mySong = new Song(nbsSong, myPlugin, "minecraft:block.note_block.harp", audience);
mySong.playSong(1.0f); // Play at full volume

// Later, to stop the song (optional)
mySong.stopSong();
```

This class provides a way to play NBS songs in Minecraft, with control over the volume and the ability to stop playback. It's designed to work within the Bukkit/Spigot plugin environment.