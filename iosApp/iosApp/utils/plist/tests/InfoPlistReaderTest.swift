//
//  InfoPlistReaderTest.swift
//  ShapeByteTests
//
//  Created by Stefan Lang (work)  on 06.01.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Testing

struct InfoPlistReaderTest {
    private let sut = InfoPlistReader()

    @Test("test Bundle identifier")
    func testBundleIdentifier() async throws {
        let bundleIdentifier = sut.bundleIdentifier
        #expect("de.stefanlang.shapebyte.development" == bundleIdentifier)
    }

    @Test("test appVersion string")
    func testAppVersionString() async throws {
        let appVersion = sut.appVersion
        #expect(!appVersion.isEmpty)
    }

    @Test("test build version")
    func testBuildVersion() async throws {
        let buildVersion = sut.buildVersion
        #expect(buildVersion != 0)
    }
}
