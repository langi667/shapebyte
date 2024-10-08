import SwiftUI
import shared

struct ContentView: View {
    @State
    var logger = LoggerInjector().logger

	var body: some View {
        Text("Hello").onAppear {
            logger.i(tag: "ContentView", message: "onAppear")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
