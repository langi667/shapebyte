//
//  DurationWrapper.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class DurationWrapper: Equatable {
    static let invalid = DurationWrapper(-1)

    let value: TimeInterval
    
    static func == (lhs: DurationWrapper, rhs: DurationWrapper) -> Bool {
        return lhs === rhs
    }

    init(_ value: TimeInterval) {
        self.value = value
    }
}
