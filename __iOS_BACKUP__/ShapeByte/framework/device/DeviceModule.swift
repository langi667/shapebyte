//
//  DeviceModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 13.08.24.
//

import Foundation

class DeviceModule {
    private static let diModule = DIModule()

    static var deviceSizeCategoryProvider: DeviceSizeCategoryProvider {
        diModule.instanceTypeOrCreate(
            type: DeviceSizeCategoryProvider.self,
            create: { DeviceSizeCategoryProvider() }
        )
    }

    private init() {}
}
