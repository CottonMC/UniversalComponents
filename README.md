<img src="icon.png" align="right" width="180px"/>

# Common Components


[>> Downloads <<](https://github.com/CottonMC/CommonComponents/releases)

*Networking!*

**This mod is open source and under a permissive license.** As such, it can be included in any modpack on any platform without prior permission. We appreciate hearing about people using our mods, but you do not need to ask to use them. See the [LICENSE file](LICENSE) for more details.

Common Components provides components on the [Cardinal Components](https://github.com/OnyxStudios/Cardinal-Components-API) framework for various interactions that are frequently used for cross-mod interaction. It's designed for compatibility and automatic integration with other providing systems, such as Vanilla's `InventoryProvider` system and AlexIIL's [Lib Block Attributes](https://github.com/AlexIIL/LibBlockAttributes).

## Inventories

`InventoryComponent`, registered as `CommonComponents.INVENTORY_COMPONENT`, provides handling for ItemStacks, similar to a vanilla `Inventory`. It is based on a test/action system, so you can know the result of a modification without mutating the inventory.

## Fluids

Watch this space!

## Data

`DataComponent`, registered as `CommonComponents.DATA_COMPONENT`, provides handling for structured read-only data usable for automation, HUD elements, and other purposes. It is based on [ProbeDataProvider](https://github.com/elytra/ProbeDataProvider) by Unascribed and Falkreon.

## Roadmap
- [x] Inventory component
  - [x] Vanilla integration
  - [ ] Lib Block Attributes integration
  - [ ] Fluidity integration
- [ ] Fluid component
  - [ ] Lib Block Attributes integration
  - [ ] TechReborn integration
  - [ ] Fluidity integration
- [x] Data component
