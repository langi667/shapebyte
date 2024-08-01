//
//  Exercise.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

/**
 Represents an exercise such as Push Up, Pull Up ...
 */
struct Exercise: Element, Equatable {
    fileprivate (set) var name: String

    func isEqualTo(_ other: Any) -> Bool {
        guard let otherAsExercise = other as? Self else {
            return false
        }

        return self == otherAsExercise
    }
}
