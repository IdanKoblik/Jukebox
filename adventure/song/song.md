---
title: Song class
editLink: true
---

The `Song` class represents a song that plays a series of notes. It extends the `AbstractSong` class and provides functionality to play NBS (Note Block Studio) songs in a Minecraft environment.

> A song can also be played in a specific position.
>

## Class Declaration

```java
public class Song extends AbstractSong {
    // ... (class implementation)
}
```

## Methods

### playSong(float volume)

Plays the song at the specified volume.

```java
public void playSong(float volume)
```

- If the song has no notes, the method returns immediately.
- Sets the `playing` flag to true.
- Iterates through each note in the song:
- Schedules note at the appropriate time.
- Calculates the pitch based on the note's key.
- Calls `playSound` with the note's instrument, calculated pitch, and specified volume.
- If `playing` becomes false during iteration, the loop breaks.

### stopSong()

Stops the currently playing song.

```java
public void stopSong()
```

- Sets the `playing` flag to false if the song is currently playing.

## Usage Example

```java
NBSSong nbsSong = // ... load NBS song
Audience audience = // ... define audience

Song mySong = new Song(nbsSong, "minecraft:block.note_block.harp", audience, null);
mySong.playSong(1.0f); // Play at full volume

// Later, to stop the song (optional)
mySong.stopSong();
```

This class provides a way to play NBS songs in Minecraft, with control over the volume and the ability to stop playback.