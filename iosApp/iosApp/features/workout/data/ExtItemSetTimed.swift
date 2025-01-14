//
//  ExtItemSetTimed.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension ItemSetTimed {
    static func forDuration(_ duration: Duration) -> ItemSetTimed {
        let components = duration.components
        let seconds = components.seconds
        let milliseconds = components.attoseconds / 1_000_000_000_000_000
        let total = ((seconds * 1000) + milliseconds )

        let itemSet = ItemSetTimedMilliseconds(milliSecsRaw: Int32(seconds))
        return itemSet
    }
}
