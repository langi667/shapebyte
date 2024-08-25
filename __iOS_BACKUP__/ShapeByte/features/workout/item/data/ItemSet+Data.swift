//
//  ItemSet+Data.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

extension ItemSet {
    enum Data: Equatable {
        case timed(timePassed: TimeInterval, timeRemaining: TimeInterval, progress: Progress, nextProgress: Progress)

        var progress: Progress {
            let retVal: Progress

            switch self {
            case .timed(_, _, let progress, _):
                retVal = progress
            }

            return retVal
        }

        var nextProgress: Progress? {
            let retVal: Progress?

            switch self {
            case .timed(_, _, _, let nextProgress):
                retVal = nextProgress
            }

            return retVal
        }
    }
}
