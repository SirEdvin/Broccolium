package site.siredvin.template.data

import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import site.siredvin.peripheralium.data.blocks.LootTableHelper
import site.siredvin.template.xplat.ModPlatform
import java.util.function.BiConsumer

object ModLootTableProvider {
    fun getTables(): List<LootTableProvider.SubProviderEntry> {
        return listOf(
            LootTableProvider.SubProviderEntry({
                LootTableSubProvider {
                    registerBlocks(it)
                }
            }, LootContextParamSets.BLOCK),
        )
    }

    fun registerBlocks(@Suppress("UNUSED_PARAMETER") consumer: BiConsumer<ResourceLocation, LootTable.Builder>) {
        val lootTable = LootTableHelper(ModPlatform.holder)
        lootTable.validate()
    }
}
