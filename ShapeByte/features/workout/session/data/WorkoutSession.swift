//
//  WorkoutSession.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

class WorkoutSession {
    static let empty = WorkoutSession(items: .init())

    var isFinished: Bool {
        return currItemIndex >= self.items.count
    }

    private var currItemIndex = -1
    private let items: [ItemGroup]

    init(items: [ItemGroup]) {
        self.items = items
    }

    func nextItemGroup() -> ItemGroup? {
        if isFinished {
            return nil
        }

        currItemIndex += 1
        return items.getOrNull(currItemIndex)
    }
}
