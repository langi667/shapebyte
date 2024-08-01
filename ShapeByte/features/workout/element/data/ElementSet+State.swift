//
//  ElementSetState.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

extension ElementSet {
    enum State: Equatable {
        case idle
        case running(setData: ElementSet.Data)
        case paused(setData: ElementSet.Data)
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
