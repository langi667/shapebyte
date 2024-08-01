//
//  ElementGroup.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 01.08.24.
//

import Foundation

/**
 Assignment of an Element like a push up or break or countdown elements  to amount of sets
 */
struct ElementGroup {
    let element: any Element
    let sets: ElementSets

    var isTimedExercise: Bool {
        return sets.isTimed && element is Exercise
    }

    var isCountdown: Bool {
        return sets.isTimed && (element as? Countdown) != nil
    }
}
