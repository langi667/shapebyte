@file:OptIn(ExperimentalObjCName::class)

package de.stefan.lang.designsystem

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

enum class FontWeight {
    @ObjCName("ultraLight")
    UltraLight,

    @ObjCName("thin")
    Thin,

    @ObjCName("light")
    Light,

    @ObjCName("regular")
    Regular,

    @ObjCName("medium")
    Medium,

    @ObjCName("semibold")
    Semibold,

    @ObjCName("bold")
    Bold,

    @ObjCName("heavy")
    Heavy,

    @ObjCName("black")
    Black,
}
