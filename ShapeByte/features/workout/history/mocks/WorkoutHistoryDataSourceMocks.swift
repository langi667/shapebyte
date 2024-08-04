//
//  WorkoutHistoryDataSourceMocks.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.08.24.
//

import Foundation
import Combine

class WorkoutHistoryDataSourceMocks: WorkoutHistoryDataSource {
    func historyFor(date: Date, untilPastDate pastDate: Date) -> [WorkoutScheduleEntry] {
        var history: [WorkoutScheduleEntry] = .init()

        let calendar = Calendar.current // Aktueller Kalender
        let components = calendar.dateComponents([.day], from: pastDate, to: date)
        var daysCount = components.day ?? 0

        for i in 0..<daysCount {
            guard let date = calendar.date(byAdding: .day, value: (i * -1), to: date) else {
                continue
            }

            let entry = WorkoutScheduleEntry(id: "\(i)", name: "Workout \(i + 1)", date: date, progress: Progress(1))
            history.append(entry)
        }

        return history
    }
}
