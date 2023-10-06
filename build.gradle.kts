import java.util.function.BiConsumer

plugins {
    java
    id("site.siredvin.root") version "0.4.17"
    id("site.siredvin.release") version "0.4.17"
    id("com.dorongold.task-tree") version "2.1.1"
}

subprojectShaking {
    withKotlin.set(false)
}

val setupSubproject = subprojectShaking::setupSubproject

subprojects {
    setupSubproject(this)
}

githubShaking {
    modBranch.set("1.19.2")
    projectRepo.set("Broccolium")
//    mastodonProjectName.set("Template")
    shake()
}

repositories {
    mavenCentral()
}