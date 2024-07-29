//
//  ExerciseSets.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 29.07.24.
//

import Foundation

struct ExerciseSets: Equatable {
    static let empty = ExerciseSets(sets: [])
    let sets: [ExerciseSet]

    var count: Int {
        return sets.count
    }
}
