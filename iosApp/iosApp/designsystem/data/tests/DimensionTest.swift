//
//  DimensionTest.swift
//  ShapeByteTests
//
//  Created by Stefan Lang on 27.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import shared

struct DimensionTest {

    @Test
    func initWithInt() async throws {
        let sut = Dimension(
            xTiny: 1,
            tiny: 2,
            small: 3,
            medium: 4,
            large: 5,
            xLarge: 6,
            xxLarge: 7,
            xxxLarge: 8
        )

        #expect(sut.xTiny == 1)
        #expect(sut.tiny == 2)
        #expect(sut.small == 3)

        #expect(sut.medium == 4)
        #expect(sut.large == 5)
        #expect(sut.xLarge == 6)

        #expect(sut.xxLarge == 7)
        #expect(sut.xxxLarge == 8)
    }

    @Test
    func initWithSharedSpacing() async throws {
        let sharedDimension = shared.Dimension(
            xTiny: 1,
            tiny: 2,
            small: 3,
            medium: 4,
            large: 5,
            xLarge: 6,
            xxLarge: 7,
            xxxLarge: 8
        )

        let sut = Dimension(sharedDimension)

        #expect(sut.xTiny == 1)
        #expect(sut.tiny == 2)
        #expect(sut.small == 3)

        #expect(sut.medium == 4)
        #expect(sut.large == 5)
        #expect(sut.xLarge == 6)

        #expect(sut.xxLarge == 7)
        #expect(sut.xxxLarge == 8)
    }
}
