//
//  WorkoutHistoryDataSource.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.08.24.
//

import Foundation
import Combine

protocol WorkoutHistoryDataSource {
    func historyFor(date: Date, untilPastDate pastDate: Date) -> [WorkoutScheduleEntry]
}
