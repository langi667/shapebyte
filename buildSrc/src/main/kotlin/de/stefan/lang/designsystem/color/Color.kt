package de.stefan.lang.designsystem.color

data class Color(val hex: String) {
    val hexWithAlpha: String
        get() {
            var color = hex.removePrefix("0x")
            val diff =  8 - color.length

            if (diff > 0) {
                color = "F".repeat(diff) + color
            }

            return "0x$color"
        }

    val hexWithAlphaNoPrefix: String
        get() = hexWithAlpha.removePrefix("0x")

    val alphaHex: String
        get() = hexWithAlphaNoPrefix.substring(0, 2)

    val redHex: String
        get() = hexWithAlphaNoPrefix.substring(2, 4)

    val greenHex: String
        get() = hexWithAlphaNoPrefix.substring(4, 6)

    val blueHex: String
        get() = hexWithAlphaNoPrefix.substring(6, 8)

    val alpha: Int
        get() = toInt(alphaHex)

    // 0 ... 1
    val alphaRelative: Double
        get() = toInt(alphaHex).toDouble() / 255.0

    val red: Int
        get() = toInt(redHex)

    val green: Int
        get() = toInt(greenHex)

    val blue: Int
        get() = toInt(blueHex)

    private fun toInt(colorString: String): Int  = colorString.toInt(16)

}