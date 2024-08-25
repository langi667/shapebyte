//
//  WorkoutHistoryEntry.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

struct WorkoutHistoryEntry: Equatable, Identifiable {
    var id: String {
        return entry.id
    }

    let entry: WorkoutScheduleEntry

    var name: String {
        return entry.name
    }

    var dateString: String {
        return DateStringFormatter.string(from: entry.date)
    }
}
