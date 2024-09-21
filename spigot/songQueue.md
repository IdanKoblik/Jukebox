---
title: SongQueue class
editLink: true
---

The `SongQueue` class manages a queue of songs to be played in sequence. It provides functionality to add songs, play them in order, and control playback.

## Class Declaration

```java
public class SongQueue {
    // ... (class implementation)
}
```

## Fields

- `Queue<Song> songs`: A queue to store Song objects.
- `Plugin plugin`: The plugin instance.
- `Audience audience`: The audience that will hear the sounds.
- `String defaultSound`: The default sound key.
- `Location location`: Optional location where the sounds will be played.
- `boolean playing`: Flag to indicate if songs are currently playing.
- `float volume`: The volume at which songs are played.

## Constructors

### SongQueue(Plugin, String, Audience)

Constructs a SongQueue with the given parameters.

```java
public SongQueue(Plugin plugin, String defaultSound, Audience audience)
```

- `plugin`: The plugin instance.
- `defaultSound`: The default sound key.
- `audience`: The audience that will hear the sound.

### SongQueue(Plugin, String, Location, Audience)

Constructs a SongQueue with the given parameters, including a location.

```java
public SongQueue(Plugin plugin, String defaultSound, Location location, Audience audience)
```

- `plugin`: The plugin instance.
- `defaultSound`: The default sound key.
- `location`: The location where the sound will be played.
- `audience`: The audience that will hear the sound.

## Methods

### addSong(NBSSong nbsSong)

Adds a song to the queue.

```java
public void addSong(NBSSong nbsSong)
```

- Creates a new `Song` object and adds it to the queue.

### playSongs(float volume)

Starts playing songs in the queue with the specified volume.

```java
public void playSongs(float volume)
```

- Sets the volume and starts playing if the queue is not empty and not already playing.

### playNextSong() (private)

Plays the next song in the queue.

- Polls the next song from the queue and plays it.
- Schedules a task to check when the song finishes and play the next one.

### stopSongs()

Stops all currently playing songs and clears the queue.

```java
public void stopSongs()
```

- Sets `playing` to false, stops all songs, and clears the queue.

### isPlaying()

Checks if any songs are currently playing.

```java
public boolean isPlaying()
```

- Returns `true` if songs are playing, otherwise `false`.

### getRemainingSongs()

Gets the number of remaining songs in the queue.

```java
public int getRemainingSongs()
```

- Returns the number of songs left in the queue.

## Usage Example

```java
Plugin myPlugin = // ... obtain plugin instance
Audience audience = // ... define audience
Location location = // ... define location (optional)

SongQueue queue = new SongQueue(myPlugin, "minecraft:block.note_block.harp", location, audience);

NBSSong song1 = // ... load first NBS song
NBSSong song2 = // ... load second NBS song

queue.addSong(song1);
queue.addSong(song2);

queue.playSongs(0.8f); // Play songs at 80% volume

// Later, to stop playback
queue.stopSongs();
```

This class provides a way to manage and play multiple NBS songs in sequence within a Minecraft environment, with control over the playback order, volume, and the ability to stop the entire queue.