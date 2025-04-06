import Foundation
import shared

public class Spacings {
    let xTiny: CGFloat
    let tiny: CGFloat
    let small: CGFloat
    let medium: CGFloat
    let large: CGFloat
    let xLarge: CGFloat
    let xxLarge: CGFloat
    let xxxLarge: CGFloat

    init(_ sharedSpacing: shared.Spacings) {
        xTiny = sharedSpacing.xTiny.cgFloat
        tiny = sharedSpacing.tiny.cgFloat
        small = sharedSpacing.small.cgFloat
        medium = sharedSpacing.medium.cgFloat
        large = sharedSpacing.large.cgFloat
        xLarge = sharedSpacing.xLarge.cgFloat
        xxLarge = sharedSpacing.xxLarge.cgFloat
        xxxLarge = sharedSpacing.xxxLarge.cgFloat
    }

    init(
        xTiny: Int,
        tiny: Int,
        small: Int,
        medium: Int,
        large: Int,
        xLarge: Int,
        xxLarge: Int,
        xxxLarge: Int
    ) {
        self.xTiny = CGFloat(xTiny)
        self.tiny = CGFloat(tiny)
        self.small = CGFloat(small)
        self.medium = CGFloat(medium)
        self.large = CGFloat(large)
        self.xLarge = CGFloat(xLarge)
        self.xxLarge = CGFloat(xxLarge)
        self.xxxLarge = CGFloat(xxxLarge)
    }
}
