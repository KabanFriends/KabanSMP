# KabanSMP-Core
This is a [Paper](https://papermc.io/software/paper) plugin for the KabanSMP server that includes all the necessary server features.
This plugin depends on the following third-party plugins:
- ProtocolLib
- Vault
- Any plugin that provides chat tags via Vault API (e.g. LuckPerms)
- Floodgate (as well as Geyser)

## How to use
1. Build the whole project (all submodules):
    ```shell
    ./gradlew build
    ```
2. Grab the plugin JAR file from `./build/libs/` directory. The file name should look like `KabanSMP-Core-<version>.jar`.
3. Place the mod JAR in your server's `plugins` directory. (Make sure that the [KabanSMP-Injector](../injector/README.md) and all necessary dependency plugins are installed)
