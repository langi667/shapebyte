//
//  ContentViewSnapshotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 23.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import PreviewSnapshotsTesting

@MainActor
struct ContentViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func contentView_Snapshots() throws {
        ContentView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
