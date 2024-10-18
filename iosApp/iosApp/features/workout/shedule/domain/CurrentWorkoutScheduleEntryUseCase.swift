//
//  CurrentWorkoutScheduleEntryUseCase.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation
import shared
// TODO: move to shared
class CurrentWorkoutScheduleEntryUseCase {
    // private let repository: WorkoutScheduleRepository

    init(/*repository: WorkoutScheduleRepository*/) {
        // self.repository = repository
    }

    func invoke() -> WorkoutScheduleEntry? {
        return WorkoutScheduleEntry(
            id: UUID().uuidString,
            name: "Leg Day",
            date: Date(),
            progress: Progress(progress: 0.7)
        )
    }
}
