import SwiftUI
import shared
import Factory

struct NavigationView<T: View>: View {
    @State private var navigationPath = NavigationPath()

    @NavigationHandler
    private var navigationHandler

    let startView: (NavigationHandling) -> T

    init(
        startView: @escaping (NavigationHandling) -> T
    ) {
        self.startView = startView
    }

    var body: some View {
        NavigationStack( path: $navigationPath ) {
            startView(navigationHandler)
            .navigationDestination(for: NavigationTarget.self) { destination in
                    switch onEnum(of: destination) {

                    case .quickWorkout(let workout):
                        timedWorkoutView(workout: workout).navigationBarBackButtonHidden()

                    case .home:
                        homeRootView()

                    default:
                        Text("Unhandled destination: \(destination)")
                    }
            }
        }
        .task {
            for await currDestination in navigationHandler.destinations {
                if currDestination is NavigationTarget.Back {
                    navigationPath.removeLast()
                } else {
                    navigationPath.append(currDestination)
                }
            }
        }
    }

    @ViewBuilder
    private func timedWorkoutView(workout: shared.NavigationTarget.QuickWorkout) -> some View {
        TimedWorkoutView(
            workoutId: workout.workoutId,
            viewModel: Container
                .shared
                .timedWorkoutViewModel
                .resolve(navigationHandler)
        )
    }

    @ViewBuilder
    private func homeRootView() -> some View {
        HomeRootView(
            viewModel: Container
                .shared
                .homeRootViewModel
                .resolve(navigationHandler)
        )
    }

}
