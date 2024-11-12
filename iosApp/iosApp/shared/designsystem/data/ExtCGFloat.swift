//
//  ExtCGFloat.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 24.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension CGFloat {
    func toDimension(max: CGFloat? = nil) -> CGFloat {
        let dimensionProvider = DPI.shared.dimensionProvider()
        let retVal: Float

        if let maxNotNull = max {
            retVal = dimensionProvider.withDimensionalAspect(
                height: Float(self),
                max: Float(maxNotNull)
            )
        } else {
            retVal = dimensionProvider.withDimensionalAspect(
                height: Float(self)
            )
        }

        return CGFloat(retVal)
    }

    func toDimensionMax() -> CGFloat {
        let retVal = toDimension(max: self)
        return retVal
    }
}
