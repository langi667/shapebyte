//
//  UIImage.swift
//  iosApp
//
//  Created by Stefan Lang on 11.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension UIImage {
    func toImage() -> Image {
        Image(uiImage: self)
    }
}

extension Image {
    init(imageAsset: ImageAsset) {
        self.init(imageAsset.fileNameWithoutEnding)
    }
}