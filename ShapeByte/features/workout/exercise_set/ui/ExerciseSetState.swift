//
//  ExerciseSetState .swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

enum ExerciseSetState: Equatable {
    case idle
    case running(setData: ExerciseSetStateData)
    case paused(setData: ExerciseSetStateData)
    case finished

    var isRunning: Bool {
        switch self {
        case .running:
            return true
        default:
            return false
        }
    }

    var isStopped: Bool {
        switch self {
        case .idle, .finished:
            return true
        default:
            return false
        }
    }
}

enum ExerciseSetStateData: Equatable {
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
