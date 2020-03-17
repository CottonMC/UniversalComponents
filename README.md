<img src="icon.png" align="right" width="180px"/>

# Common Components


[>> Downloads <<](https://github.com/CottonMC/CommonComponents/releases)

*Networking!*

**This mod is open source and under a permissive license.** As such, it can be included in any modpack on any platform without prior permission. We appreciate hearing about people using our mods, but you do not need to ask to use them. See the [LICENSE file](LICENSE) for more details.

Common Components provides components on the [Cardinal Components](https://github.com/OnyxStudios/Cardinal-Components-API) framework for various interactions that are frequently used for cross-mod integration. It's designed for compatibility and automatic integration with other providing systems, such as Vanilla's `InventoryProvider` system and AlexIIL's [Lib Block Attributes](https://github.com/AlexIIL/LibBlockAttributes).

## Inventories

`InventoryComponent`, registered as `CommonComponents.INVENTORY_COMPONENT`, provides handling for ItemStacks, similar to a vanilla `Inventory`. It is based on a test/action system, so you can know the result of a modification without mutating the inventory. Inventory components can be attached on any component provider and interact with each other seamlessly, along with two-way interaction between components, vanilla inventories, and more.

## Fluids

Watch this space!

## Data

`DataComponent`, registered as `CommonComponents.DATA_COMPONENT`, provides handling for structured read-only data usable for automation, HUD elements, and other purposes. It is based on [ProbeDataProvider](https://github.com/elytra/ProbeDataProvider) by Unascribed and Falkreon. Data is exposed as individual data elements, which can contain a text label, a double-based value bar, and a list of item stacks. Various types of resource bar are included by default inside the `UnitManager` class.

## Development
- [x] Inventory component
  - [x] Vanilla integration - vanilla to component
  - [x] Vanilla integration - component to vanilla
  - [ ] Lib Block Attributes integration - attribute to component
  - [ ] Lib Block Attributes integration - component to attribute
  - [ ] Fluidity integration - device to component
  - [ ] Fluidity integration - component to device
- [ ] Fluid component
  - [ ] Lib Block Attributes integration - attribute to component
  - [ ] Lib Block Attributes integration - component to attribute
  - [ ] Fluidity integration - device to component
  - [ ] Fluidity integration - component to device
  - [ ] TechReborn integration - tank to component
  - [ ] TechReborn integration - component to tank
- [x] Data component

## Future Plans

There are plenty of other resources that many mods want to use but Fabric has no common handling for.

Energy is the most obvious example, but also the biggest conundrum for integration. Energy has always had implementation splits for different scales or mechanics, and is at the biggest risk of value inflation arms races since it's not hard-bound to in-world vanilla resources. I haven't yet decided if I want to do anything energy-wise with Common Components, and if I do decide to add energy support, I'm not sure what form it would take. The potential forms it could take are 1) its own energy system, 2) automatic conversion between existing energy systems, or 3) a combination of both. Time will tell what happens.

Other common integration points are temperature and magical energy like mana, but those neither of those really fit very well into the lib as their own components. Temperature is almost exclusively used as read-only, so it can be expressed in a data component, and mana is highly dependent on the mod providing the magic system, so the component should belong to the magic system.

Of course, there are definitely other comopnents that could do with a common implementation. If you have any desires for components, don't hesitate to open an issue to ask for them.
