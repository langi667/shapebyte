//
//  WorkoutScheduleDatasourceMock.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

class WorkoutScheduleDatasourceMock: WorkoutScheduleDatasource {
    func currentWorkoutScheduleEntry() -> WorkoutScheduleEntry? {
        return WorkoutScheduleEntry(
            id: UUID().uuidString,
            name: "Leg Day",
            date: Date(),
            progress: Progress(0.7)
        )
    }
}
