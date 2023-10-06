package site.siredvin.template
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry
import net.fabricmc.api.ModInitializer
import net.minecraftforge.fml.config.ModConfig
import site.siredvin.peripheralium.FabricPeripheralium
import site.siredvin.peripheralium.xplat.PeripheraliumPlatform
import site.siredvin.template.common.configuration.ConfigHolder
import site.siredvin.template.fabric.FabricModInnerPlatform
import site.siredvin.template.fabric.FabricModRecipeIngredients
import site.siredvin.template.xplat.ModCommonHooks

@Suppress("UNUSED")
object FabricMod : ModInitializer {

    override fun onInitialize() {
        // Register configuration
        FabricPeripheralium.sayHi()
        ModCore.configure(FabricModInnerPlatform, FabricModRecipeIngredients)
        // Register items and blocks
        ModCommonHooks.onRegister()
        // Pretty important to setup configuration after integration loading!
        ForgeConfigRegistry.INSTANCE.register(ModCore.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC)
        PeripheraliumPlatform.registerGenericPeripheralLookup()
    }
}
