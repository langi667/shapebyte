//
//  DateStringFormatterTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import Foundation

import XCTest

final class DateStringFormatterTest: XCTest {
    func testStringFrom() throws {
        let calendar = Calendar.current
        let date = calendar.date(from: DateComponents(calendar: calendar, year: 2024, month: 12, day: 24))

        XCTAssertNotNil(date)
        let result = DateStringFormatter.string(from: date!)
        XCTAssertEqual("24.12.2024", result)
    }
}
