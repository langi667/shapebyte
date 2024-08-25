//
//  CountdownTimerMockCombine.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import Foundation

class CountdownTimerMock: CountdownTimer {
    var state: CountdownTimerState {
        return defaultImpl.state
    }

    private let defaultImpl: CountdownTimer

    init(defaultImpl: CountdownTimer) {
        self.defaultImpl = defaultImpl
    }

    func start(interval: TimeInterval, onTick: @escaping (TimeInterval) -> Void) -> any CountdownTimer {
        let mockInterval: TimeInterval = interval / 100.0

        _ = defaultImpl.start(interval: mockInterval) { _ in
            onTick(interval)
        }

        return self
    }

    func pause() {
        defaultImpl.pause()
    }

    func stop() {
        defaultImpl.stop()
    }
}
