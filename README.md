# KabanSMP
This is a [Paper](https://papermc.io/software/paper) plugin for SMP servers created by KabanFriends. The plugin includes, but not limited to:

- Teleport to your home with /home & /sethome
- Toggle PVP with /pvp
- Hardcore mode (You get hardcore hearts and a special chat tag until you die)
- Custom tablist which displays player count and current position
- Custom configurable MOTD with a random flavor text
- Bedrock Edition support via [Geyser](https://geysermc.org/) and Floodgate API

To implement some advanced features, this plugin requires [Ignite](https://github.com/vectrix-space/ignite) to allow the use of Mixin.
Mixin-related code is located in [KabanSMP-Injector](injector/README.md), a separate Ignite mod required by this plugin. 

## Disclaimer
KabanSMP is only meant to be used in servers owned by KabanFriends. Therefore, I cannot provide any support for people who
try to run this plugin on their own servers.

This plugin also uses a custom translation system that is not created by myself, of which I can't share the source code.
You will have to write your own replacement code for the `translation` subproject in order to build this plugin.

## How to use
1. Follow the instructions in [KabanSMP-Core](core/README.md) and [KabanSMP-Injector](injector/README.md) to install the
    required plugins and mods.
2. Create a directory `plugins/KabanSMP` in your server, and place the config and language files. (See `example-files` for an example config)
3. Run your server

