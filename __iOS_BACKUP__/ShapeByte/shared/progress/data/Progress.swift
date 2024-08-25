//
//  Progress.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

struct Progress: Equatable {
    static let zero = Progress(0)
    static let complete = Progress(1)

    let value: CGFloat // 0..1

    // TODO: consider method with rounding rule
    var absoluteValue: Int { // 0 .. 100
        Int((value * 100.0).rounded())
    }

    init(_ value: CGFloat) {
        self.value = Self.adjustProgress(value)
    }

    // TODO: consider rounding rule
    init(absoluteValue: Int) {
        let relativeValue = CGFloat(absoluteValue) / 100.0
        self.init(relativeValue)
    }

    private static func adjustProgress(_ progress: CGFloat) -> CGFloat {
        let retVal: CGFloat = min(1.0, progress)
        return retVal
    }
}
