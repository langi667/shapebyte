//
//  CountdownItemSetsViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import Foundation
import Combine
import SwiftUI

class CountdownItemSetsViewModel: ItemSetsViewModel {
    @Published var currentSetElapsedTimeText: String = ""
    @Published var isRunning: Bool = false

    @Published var alpha: CGFloat = 1
    @Published var scale: CGFloat = 1

    private var currentSet: Int = -1

    override func handleUIStateReceived(_ state: ItemSetsUIState) -> Bool {
        switch state {
        case .idle:
            break
        case .started:
            handleStateRunning(currentSet: currentSet)
        case .running(let currentSet, _, _, _):
            handleStateRunning(currentSet: currentSet)
        case .paused:
            break
        case .finished:
            break // TODO: set state to finish
        }

        return true
    }

    private func handleStateRunning(currentSet: Int) {
        if currentSet == self.currentSet {
            return
        }

        self.currentSet = currentSet
        self.currentSetElapsedTimeText = "\(group.count - currentSet)"

        let duration = group.itemSetFor(index: currentSet)?.duration ?? 0

        self.alpha = 1
        self.scale = 1

        withAnimation(.easeInOut(duration: duration)) {
            self.alpha = 0
            self.scale = 3 + CGFloat(currentSet)
        }
    }
}
