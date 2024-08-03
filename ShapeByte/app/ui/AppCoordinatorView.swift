//
//  ContentView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 23.07.24.
//

import SwiftUI

struct AppCoordinatorView: View {
    @StateObject
    private var coordinator = AppCoordinator()

    var body: some View {
        ZStack {
            switch coordinator.state {
            case .home:
                HomeCoordinatorView()
            }
        }
    }

    @ViewBuilder
    private func mainScreen() -> some View {
        WorkoutSessionCoordinatorView()
    }

}

#Preview {
    AppCoordinatorView()
}
