package de.stefan.lang.shapebyte.features.navigation.contract.impl

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestResolver
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRouteId
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationTarget

class NavigationRequestResolverImpl : NavigationRequestResolver {
    override fun resolve(request: NavigationRequest): NavigationTarget {
        return when (request) {
            is NavigationRequest.NavigateTo -> {
                resolveNavigateTo(request)
            }
            NavigationRequest.Back -> NavigationTarget.Back
        }
    }

    private fun resolveNavigateTo(navigateTo: NavigationRequest.NavigateTo): NavigationTarget {
        val pathComponents = navigateTo.path.split("/").filter { it.isNotBlank() }

        require(pathComponents.isNotEmpty())
        val destination = NavigationRouteId.Companion.routeIdForDomain(domain = pathComponents.first())

        when (destination) {
            NavigationRouteId.QuickWorkout -> {
                val workoutId = pathComponents.getOrNull(1)?.toIntOrNull()
                    ?: throw IllegalArgumentException("Illegal path: ${navigateTo.path}")
                return NavigationTarget.QuickWorkout(workoutId)
            }

            NavigationRouteId.Home -> {
                return NavigationTarget.Home
            }

            else -> throw IllegalArgumentException("Illegal path: ${navigateTo.path}")
        }
    }
}
