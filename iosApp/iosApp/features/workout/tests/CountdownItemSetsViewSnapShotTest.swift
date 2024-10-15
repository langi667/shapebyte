//
//  CountdownItemSetsViewSnapShotTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 15.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import XCTest
import PreviewSnapshotsTesting

final class CountdownItemSetsViewSnapShotTest: XCTestCase {
    func testCountdownItemSetsViewSnapShot() throws {
        CountdownItemSetsView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.8,
                perceptualPrecision: 0.8
            )
        )
    }
}
