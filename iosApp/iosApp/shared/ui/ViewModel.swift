//
//  ViewModel.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 17.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@MainActor
protocol ViewModel: ObservableObject, Loggable {
    var state: UIState { get set }

    /**
      Needs to be triggered manually. Can be used to start observing state
     */
    func onViewAppeared()

    /**
      Needs to be triggered manually. Can be used cancel/pause processes
     */
    func onViewDisappeared()
}
