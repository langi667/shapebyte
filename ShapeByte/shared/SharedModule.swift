//
//  SharedModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

struct SharedModule {
    static private let diModule = DIModule()

    static let logger: some Logging = diModule.instanceTypeOrCreate(
        name: "Logging",
        create: {
            DefaultLogger()
        }
    )

    private init() {}
}
