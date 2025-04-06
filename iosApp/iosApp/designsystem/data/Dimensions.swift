import Foundation
import shared

public class Dimensions {
    let xTiny: CGFloat
    let tiny: CGFloat
    let small: CGFloat
    let medium: CGFloat
    let large: CGFloat
    let xLarge: CGFloat
    let xxLarge: CGFloat
    let xxxLarge: CGFloat

    init(_ sharedDimension: shared.Dimensions) {
        xTiny = sharedDimension.xTiny.cgFloat
        tiny = sharedDimension.tiny.cgFloat
        small = sharedDimension.small.cgFloat
        medium = sharedDimension.medium.cgFloat
        large = sharedDimension.large.cgFloat
        xLarge = sharedDimension.xLarge.cgFloat
        xxLarge = sharedDimension.xxLarge.cgFloat
        xxxLarge = sharedDimension.xxxLarge.cgFloat
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
