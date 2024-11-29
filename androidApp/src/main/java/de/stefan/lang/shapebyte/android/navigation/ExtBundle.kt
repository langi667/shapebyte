package de.stefan.lang.shapebyte.android.navigation

import android.os.Bundle

fun Bundle?.workoutId(): String? = this?.getString(NavParams.WorkoutIdParam)
fun Bundle?.workoutIdOr(fallback: String): String = this?.workoutId() ?: fallback
