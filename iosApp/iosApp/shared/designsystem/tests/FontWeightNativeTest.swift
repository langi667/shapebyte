//
//  FontDescriptor+NativeTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [ShapeByte Tech] on 10.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import SwiftUI
import shared

struct FontWeightNativeTest {

    @Test("should map to correct native font weight values")
    func mapCorrectNativeValues() async throws {
        #expect(FontWeight.ultraLight.value == .ultraLight)
        #expect(FontWeight.thin.value == .thin)
        #expect(FontWeight.light.value == .light)

        #expect(FontWeight.regular.value == .regular)
        #expect(FontWeight.medium.value == .medium)
        #expect(FontWeight.regular.value == .regular)

        #expect(FontWeight.medium.value == .medium)
        #expect(FontWeight.semibold.value == .semibold)
        #expect(FontWeight.bold.value == .bold)

        #expect(FontWeight.heavy.value == .heavy)
        #expect(FontWeight.black.value == .black)
    }
}
