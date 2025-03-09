package de.stefan.lang.shapebyte.android.navigation

import android.os.Bundle
import de.stefan.lang.navigation.NavigationParams

fun Bundle?.workoutId(): String? = this?.getString(NavigationParams.workoutIdParam)
fun Bundle?.workoutIdOr(fallback: String): String = this?.workoutId() ?: fallback
