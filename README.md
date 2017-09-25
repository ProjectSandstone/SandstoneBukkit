# SandstoneBukkit

SandstoneBukkit is an SandstoneAPI implementation written on top of Bukkit API.

## Differences between Bukkit and Sandstone

Bukkit is a project that provides the means to extend the Minecraft Server (PC Edition).

Sandstone is an Project that the goal is facilitate the server modding development for different platforms and game editions. SandstoneAPI plugins works in all platforms that the API is implemented, the main API is universal, all features works in all minecraft editions (PC and PE), a different extension of API is provided to work with Edition dependant features (SandstoneAPI_PC and SandstoneAPI_PE).

# Building

First, clone the repository and enter it:

```
git clone --recursive https://github.com/ProjectSandstone/SandstoneBukkit
cd SandstoneBukkit
```

Generate type aliases with `gradlew genTypeAliases` and then build `gradlew build` (`gradlew.bat` for Windows Users).

# Contributing

If you want to contribute, there a lot of topics that we recommend you to read: [SandstoneCommon](https://github.com/ProjectSandstone/SandstoneCommon/wiki).

## CodeStyle

See [Sandstone CodeStyle](https://github.com/ProjectSandstone/SandstoneAPI/blob/master/CODE_STYLE.md)

