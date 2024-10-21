//
//  ExtCGFloatTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 24.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Testing

struct ExtCGFloatTest {

    // assumes iPhone16 screen size which is default for testing

    @Test
    func toDimensionTest() async throws {
        let value: Float = 84.0
        let sut = CGFloat(value)

        #expect(CGFloat(92.75830078125) == sut.toDimension())
    }

    @Test
    func toDimensionWithMaxTest() async throws {
        let value: Float = 84.0
        let max = CGFloat(90)
        let sut = CGFloat(value)

        #expect(max == sut.toDimension(max: max))
    }

    @Test
    func toDimensionMaxTest() async throws {
        let value: Float = 84.0
        let sut = CGFloat(value)

        #expect(CGFloat(value) == sut.toDimensionMax())
    }

}
