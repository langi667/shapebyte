//
//  WorkoutUIModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

class ElementModule {
    static private let diModule = DIModule()

    static func timedElementSetsViewModel() -> TimedElementSetsViewModel {
        return TimedElementSetsViewModel(
            logger: SharedModule.logger,
            setsHandler: self.setsHandler()
        )
    }

    static func countdownElementSetsViewModel() -> CountdownElementSetsViewModel {
        return CountdownElementSetsViewModel(
            logger: SharedModule.logger,
            setsHandler: self.setsHandler()
        )
    }

    static func setsHandler() -> ElementSetsHandler {
        return ElementSetsHandler(
            logger: SharedModule.logger,
            timedSetHandler: self.timedSetHandler,
            defaultSetHandler: self.defaultSetHandler
        )
    }

    private static let timedSetHandler: some ElementSetHandling = diModule.instanceTypeOrCreate(
        type: TimedElementSetHandler.self,
        create: {
            TimedElementSetHandler()
        }
    )

    private static let defaultSetHandler: some ElementSetHandling = diModule.instanceTypeOrCreate(
        type: DefaultElementSetHandler.self,
        create: {
            DefaultElementSetHandler()
        }
    )

    private init() {}
}
