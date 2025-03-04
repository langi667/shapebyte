import SwiftUI
import shared

extension SwiftUI.Image {
    init(_ imageResource: shared.ImageResource) {
        self.init(imageResource.fileName)
    }
}
