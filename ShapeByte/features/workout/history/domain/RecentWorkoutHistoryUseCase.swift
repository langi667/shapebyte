//
//  RecentHistoryUseCase.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.08.24.
//

import Foundation
import Combine

class RecentWorkoutHistoryUseCase: ObservableObject {
    private let repository: WorkoutHistoryRepository

    init(repository: WorkoutHistoryRepository) {
        self.repository = repository
    }

    func invoke() -> AnyPublisher<[WorkoutScheduleEntry], Never> {
        return repository.$recentHistory.eraseToAnyPublisher()
    }
}
