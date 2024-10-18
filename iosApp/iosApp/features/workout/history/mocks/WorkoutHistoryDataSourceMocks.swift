//
//  WorkoutHistoryDataSourceMocks.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation
import Combine
import shared
// TODO: move to shared
class WorkoutHistoryDataSourceMocks {
    func historyFor(date: Date, untilPastDate pastDate: Date) -> [WorkoutScheduleEntry] {
        var history: [WorkoutScheduleEntry] = .init()

        let calendar = Calendar.current // Aktueller Kalender
        let components = calendar.dateComponents([.day], from: pastDate, to: date)
        var daysCount = components.day ?? 0

        for currDayIndex in 0..<daysCount {
            guard let date = calendar.date(byAdding: .day, value: (currDayIndex * -1), to: date) else {
                continue
            }

            let entry = WorkoutScheduleEntry(
                id: "\(currDayIndex)",
                name: "Workout \(currDayIndex + 1)",
                date: date,
                progress: Progress(progress: 1)
            )
            history.append(entry)
        }

        return history
    }
}
