import SwiftUI
import shared

struct AppRootView: View {
    @State
    var logger = DPI.shared.logger

	var body: some View {
        HomeRootView(viewModel: HomeRootViewModelWrapper())
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
