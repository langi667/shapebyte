//
//  SnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 16.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct CountdownItemSetsViewSnapshotTest {
    @Test(.tags(.snapshotTest))
    func testCountdownItemSetsViewSnapShot() throws {
        CountdownItemSetsView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.8,
                perceptualPrecision: 0.8
            )
        )
    }
}
