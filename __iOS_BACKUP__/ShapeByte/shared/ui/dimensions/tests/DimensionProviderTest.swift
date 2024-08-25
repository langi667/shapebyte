//
//  DimensionProviderTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 14.08.24.
//

import XCTest

final class DimensionProviderTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testWithDimensionalAspectHeight() throws {
        // screen and default size are the same, return same height
        var sut = createSUT(
            defaultSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
            deviceScreenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size
        )

        let height: CGFloat = 40.0
        var result = sut.withDimensionalAspect(height: height)
        XCTAssertEqual(height, result)

        // screen size larger than default, height should be larger
        sut = createSUT(
            defaultSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
            deviceScreenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.large.size
        )

        result = sut.withDimensionalAspect(height: height)
        XCTAssertEqual(44.170616112, result, accuracy: 0.2)

        // screen size smaller than default, height should be smaller
        sut = createSUT(
            defaultSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
            deviceScreenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.small.size
        )

        result = sut.withDimensionalAspect(height: height)
        XCTAssertEqual(31.611374408, result, accuracy: 0.2)
    }

    func testWithDimensionalAspectHeightWithMax() throws {
        // screen size larger than default, but height should be same since max is
        var sut = createSUT(
            defaultSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
            deviceScreenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.large.size
        )

        let height: CGFloat = 40.0
        var result = sut.withDimensionalAspect(height: height, max: height)
        XCTAssertEqual(height, result)

        // screen size smaller than default, height should be smaller, but max is even smaller
        sut = createSUT(
            defaultSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
            deviceScreenSize: DeviceSizeCategoryProvider.iPhoneSizeBounds.small.size
        )

        let max = 20.0
        result = sut.withDimensionalAspect(height: height, max: max)
        XCTAssertEqual(max, result)
    }

    private func createSUT(
        defaultSize: CGSize = DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size,
        deviceScreenSize: CGSize = DeviceSizeCategoryProvider.iPhoneSizeBounds.medium.size
    ) -> DimensionProvider {
        let sut = DimensionProvider(
            defaultSize: defaultSize,
            deviceSizeCategoryProvider: DeviceSizeCategoryProvider()
                .setup(
                    screenSize: deviceScreenSize
                )
        )

        return sut
    }

}
