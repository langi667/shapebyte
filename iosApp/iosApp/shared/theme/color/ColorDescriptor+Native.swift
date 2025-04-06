import SwiftUI
import shared
// TODO: check if needed 
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
            return Theme.colors.background
        case .inversePrimary:
            return Theme.colors.inversePrimary
        case .primary:
            return Theme.colors.primary
        case .secondary:
            return Theme.colors.secondary
        }
    }
}
