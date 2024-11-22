//
//  SafeAreaProviding.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 21.11.24.
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

    var wrappedValue: EdgeInsets {
        let insets = environment.isInPreview ? SafeAreaProviderMock().insets : SafeAreaProvider.shared.insets
        return insets
    }
}
