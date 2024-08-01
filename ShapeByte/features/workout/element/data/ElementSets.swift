//
//  ExerciseSets.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

/**
 Represents all sets of an excerise or other exectued during  a workout.

 If the workout countains for example 4 Sets of push ups, this would be represented by this struct
 */
struct ElementSets: Equatable {
    static let empty = ElementSets(sets: [])

    let sets: [ElementSet]

    var count: Int {
        return sets.count
    }

    var isTimed: Bool {
        return sets.getOrNull(0)?.isTimed ?? false
    }

    func elementSetFor(index: Int) -> ElementSet? {
        let retVal = sets.getOrNull(index)
        return retVal
    }
}
