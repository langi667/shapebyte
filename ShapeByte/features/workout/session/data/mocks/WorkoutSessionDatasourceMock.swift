//
//  WorkoutSessionDatasourceImpl.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionDatasourceMock: WorkoutSessionDatasource {
    func currentWorkSession() -> WorkoutSession? {
        return WorkoutSession(
            elements: [
               Countdown(seconds: 5).toGroup(),

                ElementGroup(
                    element: Exercise(name: "Push Up"),
                    sets: ElementSets(
                        sets: [
                            ElementSet.timed(duration: 8),
                            ElementSet.timed(duration: 5),
                            ElementSet.timed(duration: 6)

                        ]
                    )
                ),

                ElementGroup(
                    element: Exercise(name: "Pull Up"),
                    sets: ElementSets(
                        sets: [
                            ElementSet.timed(duration: 5),
                            ElementSet.timed(duration: 6),
                            ElementSet.timed(duration: 7)

                        ]
                    )
                )
            ]
        )
    }
}
