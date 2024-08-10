//
//  TimerModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import Foundation


struct TimerModule {
    private static let diModule = DIModule()

    static let countdownTimer: any CountdownTimer = diModule
        .instanceTypeOrCreate(
            type: CountdownTimer.self,
            create: {
                CountdownTimerImplCombine()
            }
        )


    private init() {}
}
