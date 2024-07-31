//
//  DurationFormatter.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 30.07.24.
//

import Foundation

struct DurationFormatter {
    static func secondsToString(_ seconds: TimeInterval) -> String {
        let secondsRounded = Int(seconds.rounded(.up))
        let retVal = Self.secondsToString(Int(secondsRounded))

        return retVal
    }

    static func secondsToString(_ seconds: Int) -> String {
        let retVal = "\(seconds)"

        return retVal
    }
}
