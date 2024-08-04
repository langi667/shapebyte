//
//  CountdownElementSetsView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import SwiftUI
import Combine

struct CountdownElementSetsView: View {
    @ObservedObject var viewModel: CountdownElementSetsViewModel

    var body: some View {
        ZStack {
            BackgroundView()
            VStack {
                Text(viewModel.currentSetElapsedTimeText)
                    .title()
                    .opacity(viewModel.alpha)
                    .scaleEffect(viewModel.scale)
            }.onAppear {
                viewModel.start()
            }
        }
    }
}

struct CountdownElementSetsView_Previews: PreviewProvider {
    static let viewModel = ElementModule.countdownElementSetsViewModel()

    static var previews: some View {
        CountdownElementSetsView(
            viewModel: viewModel
        ).onAppear {
            viewModel.startWith(Countdown(seconds: 3).toGroup())
        }
    }
}
