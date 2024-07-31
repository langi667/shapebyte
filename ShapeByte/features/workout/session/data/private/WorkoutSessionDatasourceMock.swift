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
            exercises: ExerciseSets(
                sets: [
                    ExerciseSet.timed(duration: 5),
                    ExerciseSet.timed(duration: 5),
                    ExerciseSet.timed(duration: 5),
                    ExerciseSet.timed(duration: 2),
                    ExerciseSet.timed(duration: 3),
                    ExerciseSet.timed(duration: 5),
                    ExerciseSet.timed(duration: 3),
                    ExerciseSet.timed(duration: 10),
                    ExerciseSet.timed(duration: 8)
                ]
            )
        )
    }
}
