//
//  CountdownTimer.swift
//  iosApp
//
//  Created by Stefan Lang on 22.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
class CountdownTimer: Loggable {

    fileprivate(set) var interval: TimeInterval
    fileprivate(set) var ticks: Int

    private var timer: Foundation.Timer?
    private var currTick = 0

    init(interval: TimeInterval = 0, ticks: Int = 0) {
        self.interval = interval
        self.ticks = ticks
    }

    func restartWith(interval: TimeInterval, ticks: Int, onTick: @escaping (Int) -> Void) {
        stop()

        self.interval = interval
        self.ticks = ticks
        start(onTick: onTick)
    }

    func start(onTick: @escaping (Int) -> Void) {
        stop()

        guard ticks > 0 else {
            logE(message: "Cannot start countdown timer with zero count.")
            return
        }

        let interval = self.interval / TimeInterval(ticks)
        timer = Timer.scheduledTimer(
            withTimeInterval: interval,
            repeats: true ) { _ in
                self.currTick += 1

                guard self.currTick <= self.ticks else {
                    self.stop()
                    return
                }

                onTick(self.currTick)
            }
    }

    func stop() {
        timer?.invalidate()
        timer = nil
        currTick = 0

    }
}
