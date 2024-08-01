//
//  WorkoutElement.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

/**
Represents any kind of workout element, such as exercise, breaks, countdown, cooldown, warm up ...
 */

protocol Element: Equatable, Equals {
    var name: String { get }
}
