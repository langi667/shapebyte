//
//  ItemSetsUIState.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

enum ItemSetsUIState: Equatable {
    case idle
    case started(totalSets: Int)
    case running(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case paused(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case finished
}
