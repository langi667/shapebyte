//
//  SharedModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import UIKit

struct SharedModule {
    static private let diModule = DIModule()

    static let logger: some Logging = diModule.instanceTypeOrCreate(
        name: "Logging",
        create: {
            DefaultLogger()
        }
    )

    static let dimensionProvider: DimensionProvider = diModule.instanceTypeOrCreate(
        type: DimensionProvider.self,
        create: {
            DimensionProvider(deviceSizeCategoryProvider: DeviceModule
                .deviceSizeCategoryProvider
                .setup(
                    screenSize: UIScreen.main.bounds.size // TODO: consider changing that
                )
            )
        }
    )

    private init() {}
}
