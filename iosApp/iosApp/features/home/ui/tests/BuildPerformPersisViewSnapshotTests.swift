//
//  BuildPerformPersisViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 12.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct BuildPerformPersisViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func testBuildPerformPersisViewSnapshots() throws {
        BuildPerformPersistView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
