import SwiftUI
import Combine
import shared
import Factory
struct AppRootView: View {
    @ObservedObject private var viewModel = AppRootViewModel()

	var body: some View {
        ZStack(alignment: .topLeading) {
            if viewModel.state is UIStateData<AppRootViewModel> {
                NavigationView { navigationHandler in
                    HomeRootView(viewModel: Container
                        .shared
                        .homeRootViewModel
                        .resolve(navigationHandler)
                    )
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
