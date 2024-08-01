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
            logger: DefaultLogger(),
            setsCoordinator: self.setsCoordinator()
        )
    }

    static func countdownElementSetsViewModel() -> CountdownElementSetsViewModel {
        return CountdownElementSetsViewModel(
            logger: DefaultLogger(),
            setsCoordinator: self.setsCoordinator()
        )
    }

    static func setsCoordinator() -> ElementSetsCoordinator {
        return ElementSetsCoordinator(
            logger: DefaultLogger(),
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
}
