//
//  RecentHistoryUseCase.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation
import Combine
// TODO: move to shared
class RecentWorkoutHistoryUseCase: ObservableObject {
   // private let repository: WorkoutHistoryRepository

    @Published private(set) var recentHistory: [WorkoutScheduleEntry] = []

    init(/*repository: WorkoutHistoryRepository*/) {
        let today = Date()
        let pastDate = Calendar.current.date(byAdding: .day, value: -14, to: today)!

        recentHistory = WorkoutHistoryDataSourceMocks().historyFor(date: today, untilPastDate: pastDate)
        // self.repository = repository
    }

    func invoke() -> AnyPublisher<[WorkoutScheduleEntry], Never> {
        return $recentHistory.eraseToAnyPublisher()
    }
}
