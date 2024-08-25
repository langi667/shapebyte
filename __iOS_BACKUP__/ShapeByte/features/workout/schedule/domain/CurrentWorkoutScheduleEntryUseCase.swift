//
//  CurrentWorkoutScheduleEntryUseCase.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

class CurrentWorkoutScheduleEntryUseCase {
    private let repository: WorkoutScheduleRepository

    init(repository: WorkoutScheduleRepository) {
        self.repository = repository
    }

    func invoke() -> WorkoutScheduleEntry? {
        return repository.currentScheduleEntry()
    }
}
