//
//  WorkoutSessionDatasourceImpl.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionDatasourceMock: WorkoutSessionDatasource {
    func currentWorkSession() -> WorkoutSession? {
        let pushUps = Exercise(name: "Push Up")
        let pullUps = Exercise(name: "Pull Up")

        return WorkoutSession(
            items: [
               Countdown(seconds: 3).toGroup(),

                ItemGroup(
                    item: pushUps,
                    itemSets: ItemSets(
                        sets: [
                            ItemSet.timed(item: pushUps, duration: 20),
                            ItemSet.timed(item: pushUps, duration: 40),
                            ItemSet.timed(item: pushUps, duration: 60)

                        ]
                    )
                ),

                ItemGroup(
                    item: pullUps,
                    itemSets: ItemSets(
                        sets: [
                            ItemSet.timed(item: pullUps, duration: 20),
                            ItemSet.timed(item: pullUps, duration: 40),
                            ItemSet.timed(item: pullUps, duration: 60)
                        ]
                    )
                )
            ]
        )
    }
}
