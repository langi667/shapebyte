//
//  Timer.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import Foundation
import Combine

public enum CountdownTimerState {
    case idle
    case paused
    case running
    case stopped
}

public protocol CountdownTimer {
    var state: CountdownTimerState { get }

    func start(interval: TimeInterval, onTick: @escaping (_ interval: TimeInterval) -> Void) -> any CountdownTimer
    func pause()
    func stop()
}
