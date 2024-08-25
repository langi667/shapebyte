//
//  HomeRootViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
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
    @Published var recentHistory: [WorkoutHistoryEntry] = .init()

    var eventPublisher: PassthroughSubject<Event, Never> = PassthroughSubject<Event, Never>()

    private let currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase
    private let recentHistoryUseCase: RecentWorkoutHistoryUseCase

    private var cancellables: Set<AnyCancellable> = []

    init(
        currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
        recentHistoryUseCase: RecentWorkoutHistoryUseCase
    ) {
        self.currentWorkoutScheduleEntryUseCase = currentWorkoutScheduleEntryUseCase
        self.recentHistoryUseCase = recentHistoryUseCase

        self.recentHistoryUseCase.invoke()
            .map { history in
                history.map { historyEntry in
                    WorkoutHistoryEntry(entry: historyEntry)
                }
            }
            .sink { history in
                self.recentHistory = history
        }.store(in: &self.cancellables)
    }

    func onViewAppeared() {
        setup()
    }

    func onViewDisappeared() { /* No op */ }

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
