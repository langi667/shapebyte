//
//  DeviceSizeCategory.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 13.08.24.
//

import UIKit

enum DeviceSizeCategory {
    case xSmall(size: CGSize)
    case small(size: CGSize)
    case medium(size: CGSize)
    case large(size: CGSize)

    var size: CGSize {
        let retVal: CGSize

        switch self {
        case .xSmall(let size):
            retVal = size
        case .small(let size):
            retVal = size
        case .medium(let size):
            retVal = size
        case .large(let size):
            retVal = size
        }

        return retVal
    }

    var isXSmall: Bool {
        let retVal: Bool

        switch self {
        case .xSmall:
            retVal = true
        default:
            retVal = false
        }

        return retVal
    }

    var isSmall: Bool {
        let retVal: Bool

        switch self {
        case .small:
            retVal = true
        default:
            retVal = false
        }

        return retVal
    }

    var isMedium: Bool {
        let retVal: Bool

        switch self {
        case .medium:
            retVal = true
        default:
            retVal = false
        }

        return retVal
    }

    var isLarge: Bool {
        let retVal: Bool

        switch self {
        case .large:
            retVal = true
        default:
            retVal = false
        }

        return retVal
    }
}
