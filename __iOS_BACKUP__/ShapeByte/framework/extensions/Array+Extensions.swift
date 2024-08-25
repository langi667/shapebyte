//
//  Array+Extensions.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

extension Array {
    func getOrNull(_ index: Int) -> Element? {
        return index >= 0 && index < count ? self[index] : nil
    }
}
