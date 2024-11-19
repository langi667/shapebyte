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
@MainActor
class AppRootViewModel: ViewModel {
    @Published var state: UIState = UIState.Loading.shared

    // TODO: improve

    func onViewAppeared() {
        Task {
            await SafeAreaProvider.shared.detectSafeArea()
            self.state = UIStateData(data: AppRootViewModelData())

        }
    }

    func onViewDisappeared() { /* No Op */ }
}
