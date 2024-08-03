//
//  File.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import Foundation

struct WorkoutScheduleEntry: Equatable {
    let id: String
    let name: String
    let date: Date
    let progress: Progress
}
