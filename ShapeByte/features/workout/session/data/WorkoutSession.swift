//
//  WorkoutSession.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

class WorkoutSession {
    static let empty = WorkoutSession(elements: .init())

    var isFinished: Bool {
        return currElementIndex >= self.elements.count
    }

    private var currElementIndex = -1
    private let elements: [ElementGroup]

    init(elements: [ElementGroup]) {
        self.elements = elements
    }

    func nextElementGroup() -> ElementGroup? {
        if isFinished {
            return nil
        }

        currElementIndex += 1
        return elements.getOrNull(currElementIndex)
    }
}
