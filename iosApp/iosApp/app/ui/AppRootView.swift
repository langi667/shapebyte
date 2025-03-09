import SwiftUI
import Combine
import shared

struct AppRootView: View {
    @ObservedObject private var viewModel = AppRootViewModel()

	var body: some View {
        ZStack(alignment: .topLeading) {
            if viewModel.state is UIStateData<AppRootViewModel> {
                NavigationView { navigationHandler in
                    HomeRootView(navHandling: navigationHandler)
                }
            } else { // TODO: Loading State
                EmptyView()
            }

        }.onAppear {
            viewModel.onViewAppeared()
        }
        .onDisappear {
            viewModel.onViewDisappeared()
        }
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
