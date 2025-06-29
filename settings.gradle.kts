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
include(":core:internal:db")

include(":features:favorites:abstractions")
include(":features:favorites:data")
include(":features:favorites:usecases")
include(":features:favorites:entities")

include(":features:rates:abstractions")
include(":features:rates:data")
include(":features:rates:usecases")
include(":features:rates:entities")

include(":features:filters:entities")

include(":ui:common")
include(":ui:home")
include(":ui:filters")
