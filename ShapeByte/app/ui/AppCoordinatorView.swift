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
        }.onAppear {
            setupNavigationBar()
        }
    }

    private func setupNavigationBar() {
        let appearance = UINavigationBarAppearance()
        appearance.configureWithTransparentBackground()
        appearance.titleTextAttributes = [.foregroundColor: UIColor.textLight] // Setze die gewünschte Farbe
        appearance.largeTitleTextAttributes = [.foregroundColor: UIColor.textLight] // Setze die gewünschte Farbe

        UINavigationBar.appearance().standardAppearance = appearance
        UINavigationBar.appearance().scrollEdgeAppearance = appearance
    }
}

#Preview {
    AppCoordinatorView()
}
