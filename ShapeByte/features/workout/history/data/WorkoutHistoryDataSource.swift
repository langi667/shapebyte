//
//  WorkoutHistoryDataSource.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 04.08.24.
//

import Foundation
import Combine

protocol WorkoutHistoryDataSource {
    func historyFor(date: Date, untilPastDate pastDate: Date) -> [WorkoutScheduleEntry]
}
