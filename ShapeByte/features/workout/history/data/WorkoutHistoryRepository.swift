//
//  WorkoutHistoryRepository.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.08.24.
//

import Foundation
import Combine

class WorkoutHistoryRepository: ObservableObject {
    let dataSource: WorkoutHistoryDataSource

    @Published private(set) var recentHistory: [WorkoutScheduleEntry] = []

    init(dataSource: WorkoutHistoryDataSource) {
        self.dataSource = dataSource

        let today = Date()
        let pastDate = Calendar.current.date(byAdding: .day, value: -14, to: today)!

        recentHistory = Self.historyFrom(dataSource: dataSource, for: today, untilPastDate: pastDate)
    }

    func historyFor(
        date: Date,
        untilPastDate pastDate: Date
    ) -> [WorkoutScheduleEntry] {
        return dataSource.historyFor(date: date, untilPastDate: pastDate)
    }

    private static func historyFrom(
        dataSource: WorkoutHistoryDataSource,
        for date: Date,
        untilPastDate pastDate: Date
    ) -> [WorkoutScheduleEntry] {
        return dataSource.historyFor(date: date, untilPastDate: pastDate)
    }
}
