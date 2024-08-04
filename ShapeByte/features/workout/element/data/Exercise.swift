//
//  Exercise.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

/**
 Represents an exercise such as Push Up, Pull Up ...
 */
struct Exercise: Element, Equatable {

    static let none = Exercise(name: "")

    fileprivate (set) var name: String

    func isEqualTo(_ other: Any) -> Bool {
        guard let otherAsExercise = other as? Self else {
            return false
        }

        return self == otherAsExercise
    }
}
