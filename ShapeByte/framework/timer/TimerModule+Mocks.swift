//
//  TimerModule+Mocks.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import Foundation

extension TimerModule {
    private static let diModuleMocks = DIModule()

    static let countdownTimerMock: any CountdownTimer = diModuleMocks
        .instanceTypeOrCreate(
            type: CountdownTimerMock.self,
            create: {
                CountdownTimerMock(defaultImpl: Self.countdownTimer)
            }
        )
}
