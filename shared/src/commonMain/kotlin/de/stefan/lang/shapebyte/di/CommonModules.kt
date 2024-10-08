package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.utils.di.UtilsModule

val CommonMainModules = UtilsModule.module + WorkoutModule.module
val CommonMainTestModules = UtilsModule.testModule + WorkoutModule.testModule
