import SwiftUI
import shared

extension FontDescriptor {
    var font: Font {
        let font: Font
        switch onEnum(of: self) {

        case .system:
            font = .system(
                size: CGFloat(self.size),
                weight: self.weight.value
            )
        }

        return font
    }
}
