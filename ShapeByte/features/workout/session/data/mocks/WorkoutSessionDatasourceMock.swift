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
            items: [
               Countdown(seconds: 3).toGroup(),

                ItemGroup(
                    item: Exercise(name: "Push Up"),
                    itemSets: ItemSets(
                        sets: [
                            ItemSet.timed(duration: 20),
                            ItemSet.timed(duration: 40),
                            ItemSet.timed(duration: 60)

                        ]
                    )
                ),

                ItemGroup(
                    item: Exercise(name: "Pull Up"),
                    itemSets: ItemSets(
                        sets: [
                            ItemSet.timed(duration: 20),
                            ItemSet.timed(duration: 40),
                            ItemSet.timed(duration: 60)
                        ]
                    )
                )
            ]
        )
    }
}
