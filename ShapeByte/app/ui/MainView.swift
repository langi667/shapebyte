//
//  ContentView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 23.07.24.
//

import SwiftUI

struct MainView: View {
    var body: some View {
        ZStack {
            Theme.Colors.backgroundColor
            WorkoutSessionCoordinatorView()
        }
    }
}

#Preview {
    MainView()
}
