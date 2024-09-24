import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

    @State
    var logger = LoggerInjector().logger

	var body: some View {
        Text(greet).onAppear {
            logger.i(tag: "ContentView", message: "onAppear")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
