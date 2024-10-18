import SwiftUI
import shared

struct AppRootView: View {
    @State
    var logger = CommonMainModule.shared.logger

	var body: some View {
        ZStack {
            GeometryReader { geometry in
                HomeRootView( // TODO: Use Coordinator
                    viewModel: HomeRootViewModel(
                        currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase(),
                        recentHistoryUseCase: RecentWorkoutHistoryUseCase()
                    )
                )
                .environment(\.safeAreaInsets, geometry.safeAreaInsets)
            }
        }
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
