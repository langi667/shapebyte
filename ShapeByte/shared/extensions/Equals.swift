//
//  Equals.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 01.08.24.
//

import Foundation

// TODO: Look for better option to dangle around protocols and Equatable and Generics ...
protocol Equals {
    func isEqualTo(_ other: Any) -> Bool
}
