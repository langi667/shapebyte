//
//  Countdown.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 01.08.24.
//

import Foundation

struct Countdown: Element {
    private (set) var name: String = "Countdown"
    let ticks: ElementSets

    init(seconds: UInt) {
        self.ticks =  Self.createElementSets(seconds: seconds)
    }

    func toGroup() -> ElementGroup {
        return ElementGroup(element: self, sets: ticks)
    }

    private static func createElements(seconds: UInt) -> [ElementSet] {
        var elements: [ElementSet] = .init()

        for _ in 0..<seconds {
            let currElement = ElementSet.timed(duration: 1)
            elements.append( currElement )
        }

        return elements
    }

    private static func createElementSets(seconds: UInt) -> ElementSets {
         let elements = createElements(seconds: seconds)
         return ElementSets(sets: elements)
    }

    func isEqualTo(_ other: Any) -> Bool {
        return (other as? Countdown) != nil
    }
}
