//
//  File.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation
import shared

// TODO: move to shared
struct WorkoutScheduleEntry: Equatable {
    let id: String
    let name: String
    let date: Date
    let progress: shared.Progress
}
