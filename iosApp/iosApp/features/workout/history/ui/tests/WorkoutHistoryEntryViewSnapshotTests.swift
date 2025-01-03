//
//  WorkoutHistoryEntryViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct WorkoutHistoryEntryViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func workoutHistoryEntryViewSnapshots() throws {
        WorkoutHistoryEntryView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
