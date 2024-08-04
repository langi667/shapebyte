//
//  DateFormatter.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 04.08.24.
//

import Foundation

struct DateStringFormatter {
    private static let dateFormatter = DateFormatter()

    static func string(from date: Date) -> String {
        dateFormatter.dateFormat = "dd.MM.yyyy"
        return dateFormatter.string(from: date)
    }

}
