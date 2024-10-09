import SwiftUI
import shared

struct ContentView: View {
    @State
    var logger = CommonMainModule.shared.logger

	var body: some View {
        CountdownItemSetsView(viewModel: .init())
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
