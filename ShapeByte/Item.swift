//
//  Item.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 23.07.24.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date

    init(timestamp: Date) {
        self.timestamp = timestamp
    }
}
