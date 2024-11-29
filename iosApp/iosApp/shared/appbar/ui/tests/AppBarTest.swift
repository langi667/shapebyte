//
//  AppBarTest.swift
//  ShapeByteTests
//
//  Created by Stefan Lang on 25.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing

@MainActor
struct AppBarTest {
    @Test(.tags(.snapshotTest))
    func appBarSnapshots() throws {
        AppBar_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
