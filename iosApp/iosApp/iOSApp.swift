import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        KoinInitializerKt.startKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
        }
	}
}
