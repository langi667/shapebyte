//
//  DeviceInfoPropertyWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 25.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@propertyWrapper
@MainActor
struct Device {
    @Env private var environment
    private let deviceInfoMock = DeviceInfoMock()

    var wrappedValue: DeviceInfoProviding {
        if environment.isInPreview || environment.isRunningUnitTests {
            return deviceInfoMock // TODO: this needs to be retrieved by the DPI as well
        } else {
            return DPI.shared.deviceInfoProvider()
        }
    }
}
