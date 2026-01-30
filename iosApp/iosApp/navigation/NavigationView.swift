import SwiftUI
import shared

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
                        TimedWorkoutView(
                            workoutId: workout.workoutId,
                            navHandling: navigationHandler
                        ).navigationBarBackButtonHidden()

                    case .home:
                        HomeRootView(navHandling: navigationHandler)

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
}
