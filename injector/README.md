# KabanSMP-Injector
This is an [Ignite](https://github.com/vectrix-space/ignite) mod for the KabanSMP server that utilizes Mixin to alter the server logic. This mod is required to run the KabanSMP server.

## How to use
1. Build the whole project (all submodules):
    ```shell
    ./gradlew build
    ```
2. Grab the mod JAR file from `./build/libs/` directory. The file name should look like `KabanSMP-Injector-<version>.jar`.
3. Place the mod JAR in your server's `mods` directory. (Make sure that you have Ignite set up correctly)
