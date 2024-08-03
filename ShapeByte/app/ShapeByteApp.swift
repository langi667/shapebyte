//
//  ShapeByteApp.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 23.07.24.
//

import SwiftUI

@main
struct ShapeByteApp: App {
    @StateObject var coordinator = AppCoordinator()

    var body: some Scene {
        WindowGroup {
            // StickyScrollview()
            AppCoordinatorView()
        }
    }
}
