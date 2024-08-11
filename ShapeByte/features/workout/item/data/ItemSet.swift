//
//  ItemSet.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

protocol ItemProviding {
    var item: any Item { get }
}

/**
 Representing a single performance of either an excerise (push up, squat) or break or countdown
 */

enum ItemSet: Equatable, ItemProviding {
    case timed(item: any Item, duration: TimeInterval)

    var item: any Item {
        switch self {

        case .timed(let item, _):
            return item
        }
    }

    var duration: TimeInterval? {
        switch self {
        case .timed(_, let duration):
            return duration
        }
    }

    var isTimed: Bool {
        switch self {
        case .timed:
            return true
        }
    }

    // TODO: Test!
    static func == (lhs: ItemSet, rhs: ItemSet) -> Bool {
        switch (lhs, rhs) {
        case let (.timed(lhsItem, lhsDuration), .timed(rhsItem, rhsDuration)):
            return lhsItem.isEqualTo(rhsItem) && lhsDuration == rhsDuration
        }
    }
}
