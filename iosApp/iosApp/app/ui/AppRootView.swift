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

private struct AppRootNavigationView: View {
    @State private var navigationPath = NavigationPath()

    @NavigationHandler
    private var navigationHandler

    var body: some View {
        NavigationStack( path: $navigationPath ) {
            HomeRootView(navHandling: navigationHandler)

            .navigationDestination(for: NavigationDestination.self) { destination in
                switch destination {
                    case .quickWorkout(let workoutId):
                            TimedWorkoutView(
                                workoutId: workoutId,
                                navHandling: navigationHandler
                            ).navigationBarBackButtonHidden()
                    case .back:
                        EmptyView() // TODO: check !
                }
            }
        }
        .task {
            for await currDestination in navigationHandler.destinations {
                if currDestination == .back {
                    navigationPath.removeLast()
                } else {
                    navigationPath.append(currDestination)
                }
            }
        }
    }
}

struct AppRootView_Previews: PreviewProvider {
	static var previews: some View {
		AppRootView()
	}
}
