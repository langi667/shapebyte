import SwiftUI
import shared

struct AppRootView: View {
    @ObservedObject private var viewModel = AppRootViewModel()

	var body: some View {
        ZStack(alignment: .topLeading) {
            if viewModel.state is UIStateData<AppRootViewModel> {
                HomeRootView(viewModel: HomeRootViewModelWrapper())
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
