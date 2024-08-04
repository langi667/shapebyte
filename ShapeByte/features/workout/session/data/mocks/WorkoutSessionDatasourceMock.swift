//
//  WorkoutSessionDatasourceImpl.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionDatasourceMock: WorkoutSessionDatasource {
    func currentWorkSession() -> WorkoutSession? {
        return WorkoutSession(
            elements: [
               Countdown(seconds: 3).toGroup(),

                ElementGroup(
                    element: Exercise(name: "Push Up"),
                    elementSets: ElementSets(
                        sets: [
                            ElementSet.timed(duration: 20),
                            ElementSet.timed(duration: 40),
                            ElementSet.timed(duration: 60)

                        ]
                    )
                ),

                ElementGroup(
                    element: Exercise(name: "Pull Up"),
                    elementSets: ElementSets(
                        sets: [
                            ElementSet.timed(duration: 20),
                            ElementSet.timed(duration: 40),
                            ElementSet.timed(duration: 60)
                        ]
                    )
                )
            ]
        )
    }
}
