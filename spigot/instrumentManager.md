---
title: InstrumentManager
editLink: true
---

The `InstrumentManager` class is a singleton that manages a collection of musical instrument sounds for use in game contexts, particularly in Minecraft. It provides a mapping system to convert Note Block Studio (NBS) file note bytes to corresponding Minecraft (Spigot) instruments.

## Class Overview

```java
public class InstrumentManager {
    // ... (class implementation)
}
```

The `InstrumentManager` uses a `Map<Byte, Key>` to store instrument sounds, where each sound is associated with a byte value. This mapping is crucial for translating NBS file notes to Minecraft instrument sounds.

## Key Features

1. **Singleton Pattern**: Ensures only one instance of `InstrumentManager` exists.
2. **NBS to Minecraft Instrument Mapping**: Maps byte values from NBS files to Minecraft instrument sounds.
3. **Version-Specific Instrument Keys**: Supports different instrument keys across Minecraft versions.
4. **Add/Remove Instruments**: Allows adding or removing individual instrument mappings.
5. **Retrieve Instruments**: Provides a method to get an instrument sound by its byte value.
6. **Clear All Instruments**: Allows clearing all stored instrument mappings.
7. **Load instruments preset**: Supports loading a collection of instrument mappings at once.

## Usage

### Getting the Instance

```java
InstrumentManager manager = InstrumentManager.getInstance();
```

### Adding an Instrument Mapping

```java
manager.addInstrument((byte) 0, Key.key("minecraft:block.note_block.harp"));
```

### Removing an Instrument Mapping

```java
Key removedSound = manager.removeInstrument((byte) 0);
```

### Retrieving an Instrument

```java
Optional<Key> sound = manager.getInstrument((byte) 0);
```

### Clearing All Instrument Mappings

```java
manager.clear();
```

### Loading Multiple Instrument Mappings

```java
Map<Byte, Key> instruments = new HashMap<>();
// Populate the map
manager.loadInstruments(instruments);
```

## NBS File Mapping and Version Changes

The `InstrumentManager` is used to map Note Block Studio (NBS) file note bytes to Minecraft Spigot instruments. It's important to note that the instrument keys have changed across different Minecraft versions:

### Minecraft 1.8 Preset Example

```java
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
```

### Version Changes

- **Pre-1.13**: The example above shows the instrument keys used in Minecraft 1.8.
- **1.13 and later**: The instrument keys have changed. For example, `HARP` might become `minecraft:block.note_block.harp`.

To find the correct instrument keys for your specific Minecraft version, refer to the `sounds.json` file in the Minecraft assets. This file contains the most up-to-date sound mappings for each version.

## Best Practices

1. Always use the `getInstance()` method to get the `InstrumentManager` instance.
2. Update the instrument mappings according to the Minecraft version you're targeting.
3. Consult the `sounds.json` file for the correct instrument keys in your specific Minecraft version.
4. Consider creating version-specific presets to easily switch between different Minecraft versions.
5. Handle the `Optional<Key>` returned by `getInstrument()` appropriately to avoid null pointer exceptions.
6. Use the `clear()` method with caution, as it removes all stored instrument mappings.

By utilizing the `InstrumentManager`, you can efficiently manage the mapping between NBS file notes and Minecraft instrument sounds, ensuring compatibility across different game versions.