//
//  ExtItemSetTimed.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

// TODO: maybe move to common iOS
// TODO: Test

extension ItemSet.Timed {
    static func forDuration(_ duration: Duration, item: any Item) -> ItemSet.Timed {
        return ItemSet.Timed(duration: duration.components.seconds * 1000, item: item)
    }
}
