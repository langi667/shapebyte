//
//  Countdown.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import Foundation

// TODO: check if needed

struct Countdown: Item {
    private struct Tick: Item {
        private (set) var name: String = "CountdownTick"

        func isEqualTo(_ other: Any) -> Bool {
            return (other as? Tick) != nil
        }
    }

    private (set) var name: String = "Countdown"
    private static let tick = Tick()

    let ticks: ItemSets

    init(seconds: UInt) {
        self.ticks =  Self.createItemSets(seconds: seconds)
    }

    func toGroup() -> ItemGroup {
        return ItemGroup(item: self, itemSets: ticks)
    }

    func isEqualTo(_ other: Any) -> Bool {
        return (other as? Countdown) != nil
    }

    private static func createItems(seconds: UInt) -> [ItemSet] {
        var items: [ItemSet] = .init()

        for _ in 0..<seconds {
            let currItem = ItemSet.timed(item: tick, duration: 1)
            items.append( currItem )
        }

        return items
    }

    private static func createItemSets(seconds: UInt) -> ItemSets {
         let items = createItems(seconds: seconds)
         return ItemSets(sets: items)
    }
}
