package site.siredvin.template

import dan200.computercraft.api.ForgeComputerCraftAPI
import dan200.computercraft.api.pocket.PocketUpgradeSerialiser
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import site.siredvin.peripheralium.ForgePeripheralium
import site.siredvin.peripheralium.api.peripheral.IPeripheralProvider
import site.siredvin.peripheralium.xplat.PeripheraliumPlatform
import site.siredvin.template.common.configuration.ConfigHolder
import site.siredvin.template.forge.ForgeModPlatform
import site.siredvin.template.forge.ForgeModRecipeIngredients
import site.siredvin.template.xplat.ModCommonHooks
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT

@Mod(ModCore.MOD_ID)
@Mod.EventBusSubscriber(modid = ModCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ForgeMod {

    val blocksRegistry: DeferredRegister<Block> =
        DeferredRegister.create(ForgeRegistries.BLOCKS, ModCore.MOD_ID)
    val itemsRegistry: DeferredRegister<Item> =
        DeferredRegister.create(ForgeRegistries.ITEMS, ModCore.MOD_ID)
    val creativeTabRegistry: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ModCore.MOD_ID)
    val turtleSerializers = DeferredRegister.create(
        TurtleUpgradeSerialiser.registryId(),
        ModCore.MOD_ID,
    )
    val pocketSerializers = DeferredRegister.create(
        PocketUpgradeSerialiser.registryId(),
        ModCore.MOD_ID,
    )

    init {
        ForgePeripheralium.sayHi()
        // Configure configuration
        val context = ModLoadingContext.get()
        context.registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC, "${ModCore.MOD_ID}.toml")
        ModCore.configure(ForgeModPlatform, ForgeModRecipeIngredients)
        val eventBus = MOD_CONTEXT.getKEventBus()
        eventBus.addListener(this::commonSetup)
        // Register items and blocks
        ModCommonHooks.onRegister()
        blocksRegistry.register(eventBus)
        itemsRegistry.register(eventBus)
        creativeTabRegistry.register(eventBus)
        turtleSerializers.register(eventBus)
        pocketSerializers.register(eventBus)
    }

    @Suppress("UNUSED_PARAMETER")
    fun commonSetup(event: FMLCommonSetupEvent) {
        // Register peripheral provider
        PeripheraliumPlatform.registerGenericPeripheralLookup()
    }
}
