//
//  CurrentWorkoutSessionUseCase.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class CurrentWorkoutSessionUseCase {
    private let repository: WorkoutSessionRepository

    init(repository: WorkoutSessionRepository) {
        self.repository = repository
    }

    func invoke() -> WorkoutSession? {
        return self.repository.currentWorkSession()
    }
}
