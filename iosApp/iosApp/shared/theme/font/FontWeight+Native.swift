import shared
import SwiftUI

extension FontWeight {
    var value: Font.Weight {
        let weight: Font.Weight

        switch self {
        case .ultraLight:
            weight = .ultraLight
        case .thin:
            weight = .thin
        case .light:
            weight = .light
        case .regular:
            weight = .regular
        case .medium:
            weight = .medium
        case .semibold:
            weight = .semibold
        case .bold:
            weight = .bold
        case .heavy:
            weight = .heavy
        case .black:
            weight = .black
        }

        return weight
    }
}
