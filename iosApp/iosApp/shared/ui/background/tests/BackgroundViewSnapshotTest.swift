//
//  BackgroundViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 22.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct BackgroundViewSnapshotTest {
    @Test(.tags(.snapshotTest))
    func backgroundView_Snapshots() throws {
        BackgroundView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
