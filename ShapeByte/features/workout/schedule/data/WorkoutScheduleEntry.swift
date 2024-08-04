//
//  File.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

struct WorkoutScheduleEntry: Equatable {
    let id: String
    let name: String
    let date: Date
    let progress: Progress
}
