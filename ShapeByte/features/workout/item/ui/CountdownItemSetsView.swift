//
//  CountdownItemSetsView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import SwiftUI
import Combine

struct CountdownItemSetsView: View {
    @ObservedObject var viewModel: CountdownItemSetsViewModel

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

struct CountdownItemSetsView_Previews: PreviewProvider {
    static let viewModel = ItemModule.countdownItemSetsViewModel()

    static var previews: some View {
        CountdownItemSetsView(
            viewModel: viewModel
        ).onAppear {
            viewModel.startWith(Countdown(seconds: 3).toGroup())
        }
    }
}
