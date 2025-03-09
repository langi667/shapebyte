package de.stefan.lang.navigation

object NavigationRequestResolver {
    fun resolve(request: NavigationRequest): NavigationTarget {
        return when (request) {

            is NavigationRequest.NavigateTo -> {
                resolveNavigateTo(request)
            }
            NavigationRequest.Back -> NavigationTarget.Back
        }
    }

    private fun resolveNavigateTo(navigateTo: NavigationRequest.NavigateTo): NavigationTarget {
        val pathComponents = navigateTo.path.split("/").filter { it.isNotBlank() }

        if (pathComponents.isEmpty()) {
            throw IllegalArgumentException("Illegal path: ${navigateTo.path}")
        }

        val destination = pathComponents.first()
        when (destination) {
            NavigationRouteId.QUICK_WORKOUT -> {
                val workoutId = pathComponents.getOrNull(1)?.toIntOrNull()
                    ?: throw IllegalArgumentException("Illegal path: ${navigateTo.path}")
                return NavigationTarget.QuickWorkout(workoutId)
            }
            else -> throw IllegalArgumentException("Illegal path: ${navigateTo.path}")
        }

    }
}