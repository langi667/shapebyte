import SwiftUI
import shared

struct ContentView: View {
    @State
    var logger = CommonMainModule.shared.logger

	var body: some View {
        CountdownItemSetsView(viewModel: .init())
            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
