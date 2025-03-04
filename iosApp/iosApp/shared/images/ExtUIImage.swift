import SwiftUI
import shared

extension UIImage {
    func toImage() -> SwiftUI.Image {
        Image(uiImage: self)
    }
}

extension SwiftUI.Image {
    init(imageAsset: ImageAsset) {
        self.init(imageAsset.fileNameWithoutEnding)
    }
}
