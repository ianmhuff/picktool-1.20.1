# picktool-1.20.1

A tool management mod for Minecraft 1.20.1



# Pick Tool Mod

### Author: Ian Huff
### Date Modified: 9/18/2023
### Version: 0.2.3



## About:

This is a mod for Minecraft 1.20.1 that allows for fast and clean management of tools in the hotbar.
I created this mod because I found swapping tools to be a more combersome process in this game than in games with similar inventory and tool systems, such as Terraria.
This has become especially true with the recent indroduction of the hoe as a relatively common tool type in Minecraft.

In my opinion, tools take up too many valuable hotbar slots. "Saving" these slots would enable players to more efficiently build and fight in the game.
The main inspiration for this mod was the game's own "Pick Block" feature, which allows players to automatically swap block items into their hotbar as long as they are looking at the block they desire.
However, this feature only works for blocks, even though a significant portion of the player's hotbar is usually taken up by their tools.

This mod, as the name suggests, implements a "Pick Tool" feature, which behaves similarly to "Pick Block".
However, rather than equipping the block that the player is currently targeting, it instead equips the tool needed to most efficiently break that block.
It also implements a secondary "Swap Tool" feature, which simply rotates through all of the tools in a player's inventory.



## Dependencies:

- Fabric API
- Mod Menu



## Setup

When the mod is installed and first launched, the keybinds for the Pick Tool and Swap Tool functions can be changed in Options>Controls>Key Bindings under the "Pick Tool Mod" section.
The default keys for these are Z and B, respectively, chosen to prevent conflicts with other mods that I frequently use. I personally recommend mapping these functions to Mouse Button 4 and 5, if they are available.



## Other Options

Additional options for the mod can be accessed via the Mod Menu.

The available options are as follows:

### Tool Slot
This option allows you to choose which slot tools will be automatically moved to when either of the functions are used. The default setting is slot 3.

### Swap Slot
When this option is set to true, your currently selected hotbar slot will automatically be changed to your tool slot when one of the functions is used successfully.

When this option is set to false, your currently selected hotbar slot will remain unchanged when the functions are used, allowing you to continue holding the item you were holding previously.

The default setting is true.

## Check Hotbar
When this option is set to true, tools that are already in your hotbar will be included when searching for valid tools in your inventory.

When this option is set to false, tools that are already in your hotbar will be ignored when searching for valid tools, allowing you to keep some tools in your hotbar "permanently", while using another slot to swap through other tools.

The default setting is true.



## Notes

Currently, the only types of tools supported by the mod are pickaxes, shovels, axes, and hoes. I am planning on adding support for more unique tyes of tools, such as shears, in the future, as well as some configuration options for players to customize how the mod behaves with regards to these unique tool types.
