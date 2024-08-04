//
//  ElementSet+Data.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

extension ElementSet {
    enum Data: Equatable {
        case timed(timePassed: TimeInterval, timeRemaining: TimeInterval, progress: Progress)

        var progress: Progress {
            let retVal: Progress

            switch self {
            case .timed(_, _, let progress):
                retVal = progress
            }

            return retVal
        }
    }
}
