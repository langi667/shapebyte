//
//  HomeRootViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import Foundation
import Combine
import SwiftUI

class HomeRootViewModel: ViewModel, ObservableObject {
    enum Event {
        case currWorkoutScheduleEntrySelected
    }

    @Published var currWorkoutScheduleEntry: WorkoutScheduleEntry?
    @Published var currWorkoutScheduleEntryProgress: CGFloat = 0.7

    var eventPublisher: PassthroughSubject<Event, Never> = PassthroughSubject<Event, Never>()

    private let currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase

    init(currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase) {
        self.currentWorkoutScheduleEntryUseCase = currentWorkoutScheduleEntryUseCase
    }

    func onViewAppeared() {
        setup()
    }

    func onCurrentWorkoutSelected() {
        eventPublisher.send(.currWorkoutScheduleEntrySelected)
    }

    private func setup() {
        self.currWorkoutScheduleEntry = currentWorkoutScheduleEntryUseCase.invoke()

        let progress = self.currWorkoutScheduleEntry?.progress.value ?? 0

        self.currWorkoutScheduleEntryProgress = 0

        withAnimation(.easeInOut(duration: 1.0)) {
            self.currWorkoutScheduleEntryProgress = progress
        }
    }

}
