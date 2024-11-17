//
//  HomeRootViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import Foundation
import Combine
import SwiftUI
import shared

@MainActor
class HomeRootViewModelWrapper: ViewModelWrapper {
    enum Event {
        case currWorkoutScheduleEntrySelected
    }

    var wrapped: HomeRootViewModel

    @Published
    var state: UIState = UIState.Idle.shared

    @Published var currWorkoutScheduleEntry: WorkoutScheduleEntry?
    @Published var currWorkoutScheduleEntryProgress: CGFloat = 0.7
    @Published var recentHistory: [WorkoutHistoryEntry] = .init()
    @Published var quickWorkoutsState: QuickWorkoutsUIState = QuickWorkoutsUIStateHidden()

    var eventPublisher: PassthroughSubject<Event, Never> = PassthroughSubject<Event, Never>()

    init(wrapped: HomeRootViewModel = DPI.shared.homeRootViewModel()) {
        self.wrapped = wrapped
    }

    func onViewAppeared() {
        setup()
        observeState()
        wrapped.update()
    }

    func handleObservedState(_ observedState: UIState) async {
        if let viewData: HomeRootViewModelViewData = observedState.viewData() {
            handleViewData(viewData)
        }
    }

    func onViewDisappeared() { /* No op */ }

    func onCurrentWorkoutSelected() {
        eventPublisher.send(.currWorkoutScheduleEntrySelected)
    }

    private func setup() {
        self.currWorkoutScheduleEntryProgress = 0
    }

    private func handleViewData(_ viewData: HomeRootViewModelViewData) {
        updateCurrentWorkoutSchedule(viewData.currWorkoutScheduleEntry)
        updateRecentHistory(viewData.recentHistory)
        updateQuickWorkoutsState(viewData.quickWorkoutsState)
    }

    private func updateCurrentWorkoutSchedule(_ currSchedule: WorkoutScheduleEntry?) {
        self.currWorkoutScheduleEntry = currSchedule
        let progress = currSchedule?.progress.value ?? 0.0

        withAnimation(.easeInOut(duration: 1.0)) {
            self.currWorkoutScheduleEntryProgress = CGFloat(progress)
        }
    }

    private func updateRecentHistory(_ recentHistory: [WorkoutHistoryEntry]) {
        self.recentHistory = recentHistory
    }

    private func updateQuickWorkoutsState(_ state: QuickWorkoutsUIState) {
        self.quickWorkoutsState = state
    }
}
