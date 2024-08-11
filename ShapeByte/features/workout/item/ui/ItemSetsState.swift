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
    case running(currentSet: Int, totalSets: Int, currentSetProgress: Progress, totalProgress: Progress, setData: ItemSet.Data)
    case paused
    case finished

    // TODO: test
    var isRunning: Bool {
        let retVal: Bool

        switch self {
        case .idle:
            retVal = false
        case .started:
            retVal = true
        case .running:
            retVal = true
        case .paused:
            retVal = false
        case .finished:
            retVal = false
        }

        return retVal
    }

}
