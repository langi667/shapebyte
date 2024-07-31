//
//  WorkoutSession.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

struct WorkoutSession {
    static let empty = WorkoutSession(exercises: .empty)

    let exercises: ExerciseSets
}
