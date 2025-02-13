//
//  AppRootViewModel.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 19.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

// TODO: DPI
// TODO: must come from shared
@MainActor
class AppRootViewModel: ObservableObject {
    @Published var state: UIState = UIState.Loading.shared

    // TODO: improve
    func onViewAppeared() {

        Task {
             let appInitUseCase = SharedModule.shared.sharedInitializationUseCase()
            for await appInitState in appInitUseCase.flow where appInitState == .initialized {
                self.state = UIStateData(data: AppRootViewModelData())
            }
        }
    }

    func onViewDisappeared() { /* No Op */ }
}
