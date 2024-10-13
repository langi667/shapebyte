import SwiftUI
import shared

@main
struct ShapeByteApp: App {

    init() {
        KoinInitializerKt.startKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
        }
	}
}
