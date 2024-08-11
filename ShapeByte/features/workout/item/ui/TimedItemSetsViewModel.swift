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

    override func handleSetsStateReceived(_ state: ItemSetsState) -> Bool {
        switch state {
        case .idle:
            break
            case .started:
            self.ringProgress = 0
            self.currentSetIndex = 0
            self.currentSetElapsedTime = 0

     
        case .running(let setIndex, _, let currentSetProgress, let totalProgress, let setData):
            guard let setDuration = self
                .group
                .itemSetFor(index: setIndex)?.duration else {
                return true
            }

            let lastSetIndex = currentSetIndex
            if lastSetIndex != setIndex {
                self.currentSetIndex = setIndex
                self.setCountProgress = "\(setIndex + 1)/\(group.count)"

                ringProgress = 0
                let setDurationString = DurationFormatter.secondsToString(setDuration)
                descriptionText = "Perform as much \(group.item.name)s as possible in \(setDurationString) seconds"
            }

            let elapsed = ((1.0 - currentSetProgress.value) * setDuration)
            if elapsed > 0 {
                currentSetElapsedTime = Int(elapsed.rounded(.toNearestOrAwayFromZero))
                self.currentSetElapsedTimeText = DurationFormatter.secondsToString(currentSetElapsedTime)
            }

            self.currentSetProgress = currentSetProgress
            let animationDuration: TimeInterval = 1

            withAnimation( .linear(duration: animationDuration) ) {
                self.setsProgress = totalProgress
                ringProgress = setData.nextProgress?.value ?? currentSetProgress.value
            }

        case .paused:
            break // TODO: handle
        case .finished:
            break
        }

        return true
    }

}
