//
//  CountdownTimerImplCombine.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import Foundation
import Combine

class CountdownTimerImplCombine: CountdownTimer {
    private var cancellable: AnyCancellable?
    private(set) var state: CountdownTimerState = .idle

    func start(interval: TimeInterval = 1, onTick: @escaping (_ interval: TimeInterval) -> Void) -> any CountdownTimer {
        if state != .paused {
            stop()
        }

        self.state = .running
        self.cancellable = Timer
            .publish(every: interval, on: .main, in: .common)
            .autoconnect()
            .sink { _ in
                if self.state == .running {
                    onTick(interval)
                }
            }

        return self
    }

    func pause() {
        self.state = .paused
    }

    func stop() {
        self.state = .stopped
        self.cancellable?.cancel()
        self.cancellable = nil
    }
}
