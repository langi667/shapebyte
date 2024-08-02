//
//  CountdownElementSetsView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 01.08.24.
//

import SwiftUI
import Combine

struct CountdownElementSetsView: View {
    @ObservedObject var viewModel: CountdownElementSetsViewModel

    var body: some View {
        VStack {

            Text(viewModel.currentSetElapsedTimeText)
                .titlePrimaryColor()
                .opacity(viewModel.alpha)
                .scaleEffect(viewModel.scale)
        }.onAppear {
            viewModel.start()
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
