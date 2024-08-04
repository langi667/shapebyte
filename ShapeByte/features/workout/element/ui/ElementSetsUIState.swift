//
//  ElementSets+State.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

enum ElementSetsUIState: Equatable {
    case idle
    case running(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case paused(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case finished
}
