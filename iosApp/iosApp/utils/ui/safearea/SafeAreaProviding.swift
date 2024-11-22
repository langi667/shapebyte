//
//  SafeAreaProviding.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 21.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@MainActor
protocol SafeAreaProviding {
    var insets: EdgeInsets { get }
    func detectSafeArea() async
}

@propertyWrapper
@MainActor
struct SafeAreaInfo: Loggable {
    @Env private var environment

    private var safeAreaProvider: SafeAreaProviding {
        if environment.isInPreview || environment.isRunningUnitTests {
            return SafeAreaProviderMock()
        } else {
            return SafeAreaProvider.shared
        }
    }

    var wrappedValue: EdgeInsets {
        let insets = safeAreaProvider.insets
        return insets
    }
}
