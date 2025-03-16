package de.stefan.lang.designsystem.font

enum class FontWeight {
    UltraLight,
    Thin,
    Light,
    Normal,
    Medium,
    Semibold,
    Bold,
    Heavy,
    Black,

    ;

    val iOSName: String
        get() {
            return when (this) {
                UltraLight -> "ultraLight"
                Thin -> "thin"
                Light -> "light"
                Normal -> "regular"
                Medium -> "medium"
                Semibold -> "semibold"
                Bold -> "bold"
                Heavy -> "heavy"
                Black -> "black"
            }
        }
}
