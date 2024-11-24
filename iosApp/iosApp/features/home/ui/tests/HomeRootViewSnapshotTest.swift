//
//  Test.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 24.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct HomeRootViewSnapshotTest {
    @Test(.tags(.snapshotTest))
    func homeRootViewSnapshotTests() throws {
        HomeRootView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
