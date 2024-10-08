import SwiftUI
import shared

struct ContentView: View {
    @State
    var logger = UtilsModule.shared.logger

	var body: some View {
        Text("Hello").onAppear {
            print("Test")
            logger.i(tag: "ContentView", message: "onAppear")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
