//
//  SafeAreaPropertyWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 25.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@propertyWrapper
@MainActor
struct SafeAreaInfo {
    @Device
    private var deviceInfo

    var wrappedValue: EdgeInsets {
        let insets = deviceInfo.safeArea.insets
        return insets
    }
}
