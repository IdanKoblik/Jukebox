# CHANGELOG

## 0.0.5-f
- [shared] Change `NBSFile` -> `NBSSequence`, now an immutable object
- [shared] Add `NBSSequencePlayer` to handle `NBSSequence`
- [adventure] `InstrumentManager` is now an interface in `shared` module
- [adventure] Create `KyoriInstrumentManagerImpl` that impl the `InstrumentManager` class
- [shared] `NBSNote` now an immutable object

## 0.0.4
- [cicd] Rewrite the whole cicd

## 0.0.3
- [paper] New module that impl adventure api
- [adventure] Now adventure module used as abstract module for all minecraft impl's
- [shared] `AbstractSong` has been moved to `shared` module

## 0.0.2
- [spigot] Don't exist anymore
- [standalone] Renamed to `shared`
- [shared] Add more fields to `NBSSong`
- [adventure] Moved spigot to adventure by replacing spigot with more standalone functionally
- [adventure] `InstrumentManager` has default preset of mc version 1.8
- [adventure] Combined `LoopableSong` with `Song`
- [adventure] Restructured how song and abstract song works
- [adventure] Instead of using bukkit location in song now it use Cocoabeans `position`

## 0.0.1
- [spigot] Add initial impl for nbs files
- [standalone] Add nbs file parser