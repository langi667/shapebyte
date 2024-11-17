//
//  QuickWorkoutEntryViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 17.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing

@MainActor
struct QuickWorkoutEntryViewSnapshotTest {
    @Test(.tags(.snapshotTest))
    func quickWorkoutEntryViewSnapshots() throws {
        QuickWorkoutEntryView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
