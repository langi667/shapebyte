//
//  ItemGroup.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import Foundation

// TODO: remove
/**
 Assignment of an item like a push up or break or countdown items  to amount of sets
 */
struct ItemGroup: Equatable {

    static let empty = ItemGroup(
        item: Exercise.none,
        itemSets: .empty
    )

    let item: any Item
    let itemSets: ItemSets

    var isTimedExercise: Bool {
        return itemSets.isTimed && item is Exercise
    }

    var isCountdown: Bool {
        return itemSets.isTimed && (item as? Countdown) != nil
    }

    var count: Int {
        return itemSets.count
    }

    static func == (lhs: ItemGroup, rhs: ItemGroup) -> Bool {
        return lhs.item.isEqualTo(rhs.item) && lhs.itemSets == rhs.itemSets
    }

    func itemSetFor(index: Int) -> ItemSet? {
        return itemSets.itemSetFor(index: index)
    }
}
