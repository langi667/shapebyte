//
//  DeviceSizeCategoryProvider.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 13.08.24.
//

import Foundation
// TODO: move to shared
class DeviceSizeCategoryProvider {
    // swiftlint:disable:next type_name
    struct iPhoneSizeBounds {
        static let xSmall = DeviceSizeCategory.xSmall(size: CGSize(width: 320, height: 568))
        static let small = DeviceSizeCategory.small(size: CGSize(width: 375, height: 667))
        static let medium = DeviceSizeCategory.medium(size: CGSize(width: 390, height: 844))
        static let large = DeviceSizeCategory.large(size: CGSize(width: 430, height: 932))
    }

    var sizeCategory: DeviceSizeCategory {
        return Self.iPhoneSizeCategory(for: self.screenSize)
    }

    private(set) var screenSize: CGSize = .zero

    @discardableResult
    func setup(screenSize: CGSize) -> DeviceSizeCategoryProvider {
        self.screenSize = screenSize
        return self
    }

    static func iPhoneSizeCategory(for size: CGSize) -> DeviceSizeCategory {
        let height = size.height

        switch height {
        case 0...iPhoneSizeBounds.xSmall.size.height:
            return .xSmall(size: size)
        case (iPhoneSizeBounds.xSmall.size.height + 1)...iPhoneSizeBounds.small.size.height:
            return .small(size: size)
        case (iPhoneSizeBounds.small.size.height + 1)...iPhoneSizeBounds.medium.size.height:
            return .medium(size: size)
        default:
            return .large(size: size)
        }
    }
}
