//
//  ShapeByteApp.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 23.07.24.
//

import SwiftUI
import SwiftData

@main
struct ShapeByteApp: App {
    var sharedModelContainer: ModelContainer = {
        let schema = Schema([
            Item.self
        ])
        let modelConfiguration = ModelConfiguration(schema: schema, isStoredInMemoryOnly: false)

        do {
            return try ModelContainer(for: schema, configurations: [modelConfiguration])
        } catch {
            fatalError("Could not create ModelContainer: \(error)")
        }
    }()

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
        .modelContainer(sharedModelContainer)
    }
}
