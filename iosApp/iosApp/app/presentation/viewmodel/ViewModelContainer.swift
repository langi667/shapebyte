import Foundation
import Factory
import shared

@MainActor
extension Container {
    var homeRootViewModel: ParameterFactory<NavigationRequestHandler, HomeRootViewModelWrapper> {
        ParameterFactory(self) {@MainActor handler in
            HomeRootViewModelWrapper(
                viewModel: SharedModule.shared.homeRootViewModel(
                    navigationHandler: handler
                )
            )
        }.shared
    }
}
