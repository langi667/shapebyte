//
//  DeviceSizeCategoryProviderTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 13.08.24.
//

import XCTest

final class DeviceSizeCategoryProviderTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testXSmallPhoneSizeDetection() throws {
        let sut = DeviceSizeCategoryProvider()
            .setup(
                screenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.xSmall.size
            )

        XCTAssertTrue(sut.sizeCategory.isXSmall)
        XCTAssertFalse(sut.sizeCategory.isSmall)
        XCTAssertFalse(sut.sizeCategory.isMedium)
        XCTAssertFalse(sut.sizeCategory.isLarge)
    }

    func testSmallPhoneSizeDetection() throws {
        let sut = DeviceSizeCategoryProvider()
            .setup(
                screenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.small.size
            )

        XCTAssertFalse(sut.sizeCategory.isXSmall)
        XCTAssertTrue(sut.sizeCategory.isSmall)
        XCTAssertFalse(sut.sizeCategory.isMedium)
        XCTAssertFalse(sut.sizeCategory.isLarge)
    }

    func testMediumPhoneSizeDetection() throws {
        let sut = DeviceSizeCategoryProvider()
            .setup(
                screenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size
            )

        XCTAssertFalse(sut.sizeCategory.isXSmall)
        XCTAssertFalse(sut.sizeCategory.isSmall)
        XCTAssertTrue(sut.sizeCategory.isMedium)
        XCTAssertFalse(sut.sizeCategory.isLarge)
    }

    func testLargePhoneSizeDetection() throws {
        let sut = DeviceSizeCategoryProvider()
            .setup(
                screenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.large.size
            )

        XCTAssertFalse(sut.sizeCategory.isXSmall)
        XCTAssertFalse(sut.sizeCategory.isSmall)
        XCTAssertFalse(sut.sizeCategory.isMedium)
        XCTAssertTrue(sut.sizeCategory.isLarge)
    }
}
