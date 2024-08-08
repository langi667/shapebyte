//
//  TimedItemSetsViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import Combine
import SwiftUI

class TimedItemSetsViewModel: ItemSetsViewModel {
    @Published var setsProgress: Progress = Progress(0)
    @Published var setCountProgress: String = ""

    @Published var currentSetProgress: Progress = .zero
    @Published var currentSetElapsedTime: Int = 0
    @Published var currentSetIndex: Int = -1
    @Published var currentSetElapsedTimeText: String = ""

    @Published var ringProgress: CGFloat = 0
    @Published var descriptionText: String = ""

    override func handleUIStateReceived(_ state: ItemSetsUIState) {
        switch state {
        case .idle:
            break
            case .started:
            self.ringProgress = 0
            self.currentSetIndex = 0
            self.currentSetElapsedTime = 0

        case .running(let setIndex, _, let currentSetProgress, let totalProgress):
            guard let setDuration = self
                .group
                .itemSetFor(index: setIndex)?.duration else {
                return
            }

            let lastSetIndex = currentSetIndex
            if lastSetIndex != setIndex {
                self.currentSetIndex = setIndex
                self.setCountProgress = "\(setIndex + 1)/\(group.count)"

                ringProgress = 0
                let setDurationString = DurationFormatter.secondsToString(setDuration)
                descriptionText = "Perform as much \(group.item.name)s as possible in \(setDurationString) seconds"

                withAnimation( .linear(duration: setDuration) ) {
                    ringProgress = 1
                }
            }

            let elapsed = ((1.0 - currentSetProgress.value) * setDuration)
            if elapsed > 0 {
                currentSetElapsedTime = Int(elapsed.rounded(.toNearestOrAwayFromZero))
                self.currentSetElapsedTimeText = DurationFormatter.secondsToString(currentSetElapsedTime)
            }

            self.currentSetProgress = currentSetProgress
            let animationDuration: TimeInterval

            if totalProgress.value == 0 {
                animationDuration = 0
            } else {
                animationDuration = setDuration
            }

            withAnimation( .linear(duration: animationDuration) ) {
                self.setsProgress = totalProgress
            }

        case .paused:
            break // TODO: handle
        case .finished:
            break
        }
    }
}
