import SwiftUI

enum RoundedImageButtonAppearance: CaseIterable {
    case small
    case medium
    case large

    var size: CGFloat {
        let retVal: CGFloat
        switch self {
        case .small:
            retVal = Theme.dimensions.medium - Theme.dimensions.xTiny
        case .medium:
            retVal = Theme.dimensions.medium
        case .large:
            retVal = Theme.dimensions.xLarge
        }

        return retVal
    }
}
