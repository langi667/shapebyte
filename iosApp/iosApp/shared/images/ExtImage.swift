//
//  ExtImage.swift
//  iosApp
//
//  Created by Stefan Lang (work)  on 28.01.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SwiftUI.Image {
    init(_ imageResource: shared.ImageResource) {
        self.init(imageResource.fileName)
    }
}
