//
//  ExtItemSetTimed.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension ItemSetTimed {
    static func forDuration(_ duration: Duration) -> ItemSetTimed {

        let components = duration.components
        let seconds = components.seconds
        let attoseconds = components.attoseconds

        // Convert seconds to milliseconds
        let millisecondsFromSeconds = seconds * 1_000

        // Convert attoseconds to milliseconds
        let millisecondsFromAttoseconds = attoseconds / 1_000_000_000_000_000
        let milliseconds = millisecondsFromSeconds + millisecondsFromAttoseconds

        let itemSet = ItemSetTimed(duration: milliseconds)
        return itemSet
    }
}
