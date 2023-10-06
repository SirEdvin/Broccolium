@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("site.siredvin.vanilla")
    id("site.siredvin.publishing")
}

val modVersion: String by extra
val minecraftVersion: String by extra
val modBaseName: String by extra

baseShaking {
    projectPart.set("common")
    shake()
}

repositories {
    // location of the maven that hosts JEI files since January 2023
    maven {
        url = uri("https://jitpack.io")
        content {
            includeGroupByRegex("com.github.*")
        }
    }
}

vanillaShaking {
    shake()
}

dependencies {
    implementation(libs.bundles.cccommon) {
        exclude("net.fabricmc.fabric-api")
        exclude("com.terraformersmc", "modmenu")
        exclude("net.fabricmc", "fabric-loader")
    }
}

publishingShaking {
    shake()
}
