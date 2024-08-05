//
//  ItemSet.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

extension ItemSet {
    enum State: Equatable {
        case idle
        case running(setData: ItemSet.Data)
        case paused(setData: ItemSet.Data)
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
}
