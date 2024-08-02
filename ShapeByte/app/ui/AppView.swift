//
//  ContentView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 23.07.24.
//

import SwiftUI

struct AppView: View {
    @State private var showSplashscreen = true

    var body: some View {
        ZStack {
            mainScreen()
        }
    }

    @ViewBuilder
    private func mainScreen() -> some View {
        ZStack {
            Theme.Colors.backgroundColor.ignoresSafeArea()
            WorkoutSessionCoordinatorView()
        }
    }
}

#Preview {
    AppView()
}
