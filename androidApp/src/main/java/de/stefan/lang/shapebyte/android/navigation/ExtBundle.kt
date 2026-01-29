package de.stefan.lang.shapebyte.android.navigation

import android.os.Bundle

fun Bundle?.workoutId(): String? = this?.getString(de.stefan.lang.shapebyte.features.navigation.contract.NavigationParams.workoutIdParam)
fun Bundle?.workoutIdOr(fallback: String): String = this?.workoutId() ?: fallback
