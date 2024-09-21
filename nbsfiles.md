---
title: Understanding NBS File Handling with NBSParser
editLink: true
---

# Understanding NBS File Handling with NBSParser

The **NBSParser** class provides an essential bridge for working with Noteblock Studio (NBS) files, enabling developers and music creators to read and write musical compositions in this format. In this post, we’ll explore how the reading and writing functionalities work, breaking down the code for clarity.

## What are NBS Files?

NBS files store musical data created using Noteblock Studio, a popular tool for composing music in Minecraft. These files contain information about song length, author details, tempo, and the individual notes that make up the composition.

## Reading NBS Files

The `readNBS(File file)` method is responsible for reading an NBS file and converting its content into an `NBSSong` object. Here’s a step-by-step breakdown of the process:

### Step-by-Step Breakdown

1. **Initialize Data Stream**: The method begins by initializing a `DataInputStream` to read the file. This allows for efficient reading of binary data.

2. **Read Metadata**: It extracts key information:
   - **Length and Height**: These short values represent the dimensions of the song.
   - **Song Details**: Strings for the song's name, author, and original author.
   - **Description**: Provides context for the song.
   - **Tempo**: The tempo is read as a short and converted into ticks per second.

3. **Skip Unused Data**: The method reads several integers and booleans that are not used in this implementation, such as auto-save data and click counts.

4. **Read Notes**: The core functionality involves reading notes:
   - The loop continues until a jumpTicks of `0` is encountered, indicating the end of notes.
   - For each note, the tick position, layer, instrument, and key are read, creating `NBSNote` objects that are added to a list.

5. **Return NBSSong**: Finally, an `NBSSong` object is created with the gathered metadata and note list, representing the song in memory.

## Writing NBS Files

The `writeNBS(NBSSong song, File file)` method handles the encoding of an `NBSSong` object back into the NBS file format. Here’s how it works:

### Step-by-Step Breakdown

1. **Initialize Data Stream**: A `DataOutputStream` is used to write binary data to the specified file.

2. **Write Header**: The method begins by writing the header information:
   - Length of notes, fixed height, song name, and other metadata similar to the read process.
   - Tempo is scaled back from ticks per second to short.

3. **Write Notes**: The notes are written in a similar format:
   - For each `NBSNote`, it calculates the difference in ticks from the last note to write the jumpTicks.
   - The layer is adjusted (adding 1) since layers are expected to start from 1 in the file.
   - Each note's instrument and key are written, followed by an end-of-tick marker.

4. **End Markers**: The method writes `0` to signify the end of notes and layers.

## Conclusion

The **NBSParser** class efficiently manages the conversion between NBS files and `NBSSong` objects, making it easier for developers to manipulate musical compositions programmatically. Understanding this process can help you create, modify, and save your music projects in Noteblock Studio seamlessly.

Whether you’re a developer looking to integrate NBS file support into your applications or a musician wanting to automate your compositions, the **NBSParser** class serves as a robust starting point for working with music in the Minecraft universe.
