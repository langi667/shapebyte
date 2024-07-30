//
//  Set.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation

enum ExerciseSet: Equatable {
    case timed(duration: TimeInterval)

    var duration: TimeInterval {
        switch self {
        case .timed(let duration):
            return duration
        }
    }
}
