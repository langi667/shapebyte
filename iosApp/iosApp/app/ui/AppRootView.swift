import SwiftUI
import Combine
import shared

struct AppRootView: View {
    @ObservedObject private var viewModel = AppRootViewModel()

	var body: some View {
        ZStack(alignment: .topLeading) {
            if viewModel.state is UIStateData<AppRootViewModel> {
                AppRootNavigationView()
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

@MainActor
private struct AppRootNavigationView: View {
    @State private var navigationPath = NavigationPath()

    var body: some View {
        NavigationStack( path: $navigationPath ) {
            HomeRootView()

            .navigationDestination(for: NavigationDestination.self) { destination in
                switch destination {
                case .quickWorkout(let workoutId):
                        TimedWorkoutView(workoutId: workoutId)
                            .navigationBarBackButtonHidden()
                }
            }
        }
        .task {
            for await currDestination in NavigationHandler.shared.destinations {
                navigationPath.append(currDestination)
            }
        }
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
