---
title: LoopableSong class
editLink: true
---

The `LoopableSong` class is similar to the `Song` class from the previous page, it provide an optiopn to loop the song that your currently playing.

> A song can also be played in a specific location you just need to define audiance as world
>

## Enabling looping
```java
public void setLoop(boolean loop) {
    this.loop = loop;
}
```

This method tells the api that this cuurent song can be looped.

## Usage Example

```java
Plugin myPlugin = // ... obtain plugin instance
NBSSong nbsSong = // ... load NBS song
Audience audience = // ... define audience

LoopableSong mySong = new LoopableSong(nbsSong, myPlugin, "minecraft:block.note_block.harp", audience);
mySong.setLoop(true);
mySong.playSong(1.0f); // Play at full volume
```

This class provides a way to play NBS songs and loop them in Minecraft, with control over the volume and the ability to stop playback. It's designed to work within the Bukkit/Spigot plugin environment.