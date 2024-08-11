//
//  ItemSetsState.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

enum ItemSetsState: Equatable {
    case idle
    case started(totalSets: Int)
    case running(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress)
    case paused
    case finished

    // TODO: test
    var isRunning: Bool {
        let retVal: Bool

        switch self {
        case .idle:
            retVal = false
        case .started(let totalSets):
            retVal = true
        case .running(let currentSet, let totalSets, let currentSetProgress, let totalProgress):
            retVal = true
        case .paused:
            retVal = false
        case .finished:
            retVal = false
        }

        return retVal
    }

}
