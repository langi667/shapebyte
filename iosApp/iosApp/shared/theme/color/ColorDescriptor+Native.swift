import SwiftUI
import shared

extension ColorDescriptor {
    var color: Color {
        let color: Color

        switch onEnum(of: self) {
        case .hex:
            color = Color("none") // TODO: implement

        case .namedAsset(let namedAsset):
            color = Color(namedAsset.value)
        case .themed(let themedColor):
            color = themedColorToColor(themedColor)
        }

        return color
    }

    private func themedColorToColor(_ themedColor: any ColorDescriptorThemed) -> Color {
        switch onEnum(of: themedColor) {

        case .background:
            return Theme.Colors.backgroundColor
        case .inversePrimary:
            return Theme.Colors.inversePrimaryColor
        case .primary:
            return Theme.Colors.primaryColor
        case .secondary:
            return Theme.Colors.secondaryColor
        }
    }
}
