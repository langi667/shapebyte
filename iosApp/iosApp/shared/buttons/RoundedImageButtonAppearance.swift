import SwiftUI

enum RoundedImageButtonAppearance: CaseIterable {
    case small
    case medium
    case large

    var size: Double {
        let retVal: Double
        switch self {
        case .small:
            retVal = .dimensionMedium - .dimensionXTiny
        case .medium:
                retVal = .dimensionMedium
        case .large:
                retVal = .dimensionXLarge
        }

        return retVal
    }
}
