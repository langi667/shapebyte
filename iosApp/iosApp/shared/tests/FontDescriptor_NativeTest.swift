//
//  FontWeight+NativeTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 10.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import SwiftUI
import shared

struct FontDescriptor_NativeTest {

    @Test("Should map FontDescriptor for System to correct Native font")
    func testNative() async throws {
        let sut = FontDescriptorSystem(size: 17, weight: .bold)
        #expect(Font.system(size: 17, weight: .bold) == sut.font )
    }

}
