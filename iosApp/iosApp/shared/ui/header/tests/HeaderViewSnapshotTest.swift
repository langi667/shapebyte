//
//  HeaderViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 12.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct HeaderViewSnapshotTest {

    @Test(.tags(.snapshotTest))
    func testHeaderViewSnapshot() throws {
        HeaderView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.8,
                perceptualPrecision: 0.8
            )
        )
    }
}
