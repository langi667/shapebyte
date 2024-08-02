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
struct ElementGroup: Equatable {

    static let empty = ElementGroup(
        element: Exercise.none,
        elementSets: .empty
    )

    let element: any Element
    let elementSets: ElementSets

    var isTimedExercise: Bool {
        return elementSets.isTimed && element is Exercise
    }

    var isCountdown: Bool {
        return elementSets.isTimed && (element as? Countdown) != nil
    }

    var count: Int {
        return elementSets.count
    }

    static func == (lhs: ElementGroup, rhs: ElementGroup) -> Bool {
        return lhs.element.isEqualTo(rhs.element) && lhs.elementSets == rhs.elementSets
    }

    func elementSetFor(index: Int) -> ElementSet? {
        return elementSets.elementSetFor(index: index)
    }
}
