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
            setsCoordinator: self.setsCoordinator()
        )
    }

    static func countdownElementSetsViewModel() -> CountdownElementSetsViewModel {
        return CountdownElementSetsViewModel(
            logger: SharedModule.logger,
            setsCoordinator: self.setsCoordinator()
        )
    }

    static func setsCoordinator() -> ElementSetsCoordinator {
        return ElementSetsCoordinator(
            logger: SharedModule.logger,
            timedSetCoordinator: self.timedSetCoordinator,
            defaultSetCoordinator: self.defaultSetCoordinator
        )
    }

    private static let timedSetCoordinator: some ElementSetCoordinating = diModule.instanceTypeOrCreate(
        type: TimedElementSetCoordinator.self,
        create: {
            TimedElementSetCoordinator()
        }
    )

    private static let defaultSetCoordinator: some ElementSetCoordinating = diModule.instanceTypeOrCreate(
        type: DefaultElementSetCoordinator.self,
        create: {
            DefaultElementSetCoordinator()
        }
    )

    private init() {}
}
