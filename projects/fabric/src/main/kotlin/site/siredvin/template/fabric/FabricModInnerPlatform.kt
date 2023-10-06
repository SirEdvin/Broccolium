package site.siredvin.template.fabric

import site.siredvin.peripheralium.fabric.FabricBaseInnerPlatform
import site.siredvin.template.ModCore

object FabricModInnerPlatform : FabricBaseInnerPlatform() {
    override val modID: String
        get() = ModCore.MOD_ID

}
