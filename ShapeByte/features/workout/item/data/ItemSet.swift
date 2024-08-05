//
//  ItemSet.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

/**
 Representing a single performance of either an excerise (push up, squat) or break or countdown
 */

enum ItemSet: Equatable {
    case timed(duration: TimeInterval)

    var duration: TimeInterval? {
        switch self {
        case .timed(let duration):
            return duration
        }
    }

    var isTimed: Bool {
        switch self {
        case .timed:
            return true
        }
    }
}
