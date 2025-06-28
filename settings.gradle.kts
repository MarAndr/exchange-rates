pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ExchangeRates"
include(":app")

include(":core:loading")
include(":core:remote")

include(":features:favorites:api")
include(":features:favorites:impl")
include(":features:rates:api")
include(":features:rates:impl")

include(":ui:common")
include(":ui:home")
