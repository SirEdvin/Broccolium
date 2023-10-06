package site.siredvin.template.forge

import dan200.computercraft.api.pocket.PocketUpgradeSerialiser
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import site.siredvin.peripheralium.forge.ForgeBaseInnerPlatform
import site.siredvin.template.ForgeMod
import site.siredvin.template.ModCore

object ForgeModPlatform : ForgeBaseInnerPlatform() {
    override val modID: String
        get() = ModCore.MOD_ID

    override val itemsRegistry: DeferredRegister<Item>
        get() = ForgeMod.itemsRegistry

    override val blocksRegistry: DeferredRegister<Block>
        get() = ForgeMod.blocksRegistry

    override val turtleSerializers: DeferredRegister<TurtleUpgradeSerialiser<*>>?
        get() = ForgeMod.turtleSerializers

    override val pocketSerializers: DeferredRegister<PocketUpgradeSerialiser<*>>?
        get() = ForgeMod.pocketSerializers

}
