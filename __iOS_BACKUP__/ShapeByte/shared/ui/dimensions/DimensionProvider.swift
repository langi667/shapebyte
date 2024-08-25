//
//  DimensionProvider.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 14.08.24.
//

import Foundation

// TODO: test
class DimensionProvider {
    let defaultSize: CGSize
    lazy var deviceSizeCategory: DeviceSizeCategory = deviceSizeCategoryProvider.sizeCategory

    var screenSize: CGSize {
        return deviceSizeCategory.size
    }

    private let deviceSizeCategoryProvider: DeviceSizeCategoryProvider

    init(
        defaultSize: CGSize = DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
        deviceSizeCategoryProvider: DeviceSizeCategoryProvider
    ) {
        self.defaultSize = defaultSize
        self.deviceSizeCategoryProvider = deviceSizeCategoryProvider
    }

    func withDimensionalAspect(height: CGFloat, max: CGFloat? = nil) -> CGFloat {
        let heightFactor = screenSize.height / defaultSize.height
        let result = height * heightFactor

        guard let maxNotNil = max else {
            return result
        }

        return min(maxNotNil, result)
    }
}
