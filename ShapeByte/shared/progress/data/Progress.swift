//
//  Progress.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation

// TODO: test
struct Progress: Equatable {
    static let zero = Progress(0)

    let value: CGFloat // 0..1

    // TODO: consider method with rounding rule
    var absoluteValue: Int { // 0 .. 100
        Int((value * 100.0).rounded())
    }

    init(_ value: CGFloat) {
        self.value = Self.adjustProgress(value)
    }

    // TODO: test
    // TODO: consider rounding rule
    init(absoluteValue: Int) {
        let relativeValue = CGFloat(absoluteValue) / 100.0
        self.init(relativeValue)
    }

    private static func adjustProgress(_ progress: CGFloat) -> CGFloat {
        let retVal: CGFloat
        if progress > 1.0 {
            retVal = 1.0
        } else if progress < 0.0 {
            retVal = 0.0
        } else {
            retVal = progress
        }

        return retVal
    }
}
