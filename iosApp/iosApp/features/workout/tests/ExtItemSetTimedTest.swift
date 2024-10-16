//
//  ExtItemSetTimedTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 16.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import shared

struct ExtItemSetTimedTest {

    @Test(
        "Test creating ItemSet.Timed with forDuration",
        arguments: [0, 10_0000, 200_000]
    )
    func testForDuration(_ durationRaw: Int64) async throws {
        let item: Item = None.shared
        let expected = ItemSet.Timed(duration: durationRaw, item: item)

        let expectedSeconds: Duration = .seconds( durationRaw / 1000 )
        let result = ItemSet.Timed.forDuration(expectedSeconds, item: item)

        #expect(expected.duration == result.duration)
        #expect(expected.item === result.item)
    }
}
