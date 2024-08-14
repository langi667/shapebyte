//
//  DeviceSizeCategoryTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 13.08.24.
//

import XCTest

final class DeviceSizeCategoryTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testSize() throws {
        var size: CGSize = CGSize(width: 320, height: 568)
        var sut: DeviceSizeCategory = DeviceSizeCategory.xSmall(size: size)
        XCTAssertEqual(size, sut.size)

        size = CGSize(width: 375, height: 667)
        sut = DeviceSizeCategory.small(size: size)
        XCTAssertEqual(size, sut.size)

        size = CGSize(width: 390, height: 844)
        sut = DeviceSizeCategory.medium(size: size)
        XCTAssertEqual(size, sut.size)

        size = CGSize(width: 430, height: 932)
        sut = DeviceSizeCategory.medium(size: size)
        XCTAssertEqual(size, sut.size)
    }

    func testIsXSmall() throws {
        let size: CGSize = CGSize(width: 320, height: 568)
        let sut: DeviceSizeCategory = DeviceSizeCategory.xSmall(size: size)

        XCTAssertTrue(sut.isXSmall)
        XCTAssertFalse(sut.isSmall)
        XCTAssertFalse(sut.isMedium)
        XCTAssertFalse(sut.isLarge)
    }

    func testIsSmall() throws {
        let size: CGSize = CGSize(width: 320, height: 667)
        let sut: DeviceSizeCategory = DeviceSizeCategory.small(size: size)

        XCTAssertFalse(sut.isXSmall)
        XCTAssertTrue(sut.isSmall)
        XCTAssertFalse(sut.isMedium)
        XCTAssertFalse(sut.isLarge)
    }

    func testIsMedium() throws {
        let size: CGSize = CGSize(width: 390, height: 844)
        let sut: DeviceSizeCategory = DeviceSizeCategory.medium(size: size)

        XCTAssertFalse(sut.isXSmall)
        XCTAssertFalse(sut.isSmall)
        XCTAssertTrue(sut.isMedium)
        XCTAssertFalse(sut.isLarge)
    }

    func testIsLarge() throws {
        let size: CGSize = CGSize(width: 430, height: 932)
        let sut: DeviceSizeCategory = DeviceSizeCategory.large(size: size)

        XCTAssertFalse(sut.isXSmall)
        XCTAssertFalse(sut.isSmall)
        XCTAssertFalse(sut.isMedium)
        XCTAssertTrue(sut.isLarge)
    }
}
