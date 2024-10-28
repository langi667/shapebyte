import SwiftUI
import shared

struct AppRootView: View {
    @State
    var logger = DPI.shared.logger

    @StateObject
    var vm: CountdownItemSetsViewModelWrapper = .init()

	var body: some View {
        ZStack {
            GeometryReader { geometry in
                CountdownItemSetsView(viewModel: vm)


//                HomeRootView( // TODO: Use Coordinator
//                    viewModel: HomeRootViewModelWrapper()
//                )
//                .environment(\.safeAreaInsets, geometry.safeAreaInsets)
            }
        }
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
