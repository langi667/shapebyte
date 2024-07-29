//
//  SetsState.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation

enum ExerciseSetsState: Equatable {
    case idle
    case running(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case paused(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case finished
}
