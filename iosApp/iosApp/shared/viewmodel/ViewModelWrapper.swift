import Foundation
import shared

@Observable
@MainActor
open class ViewModelWrapper<T: shared.SharedViewModel>:
    Observable,
    Loggable,
    @MainActor shared.UIStateUpdater,
    @MainActor shared.UIEventUpdater,
    @MainActor shared.UIIntentHandler,
    @MainActor ViewLifeCycleReceiver {
    public fileprivate(set) var viewState: UIState = .Idle()
    public fileprivate(set) var event: UIEvent?

    private let wrapped: T
    private var stateObservationTask: Task<Void, Never>?
    private var eventObservationTask: Task<Void, Never>?

    required public init(viewModel: T) {
        self.wrapped = viewModel
    }

    public func updateState(_ newState: UIState) {
        self.wrapped.updateState(newState)
    }

    public func emitEvent(_ event: any UIEvent) {
        wrapped.emitEvent(event)
    }

    public func handleIntent(_ event: any UIIntent) {
        wrapped.handleIntent(event)
    }

    func onViewAppeared() {
        startStateObservation()
        startEventObservation()
    }

    func onViewDisappeared() {
        stopStateObservation()
        stopEventObservation()
    }

    func startStateObservation() {
        stopStateObservation()

        let task = Task { [weak self] in
            guard let self else { return }

            for await currState in wrapped.state {
                self.viewState = currState
            }
        }

        self.stateObservationTask = task
    }

    func stopStateObservation() {
        stateObservationTask?.cancel()
        stateObservationTask = nil
    }

    private func startEventObservation() {
        stopEventObservation()

        let task = Task { [weak self] in
            guard let self else { return }

            for await currEvent in wrapped.eventFlow {
                self.event = currEvent
            }
        }

        self.stateObservationTask = task
    }

    private func stopEventObservation() {
        eventObservationTask?.cancel()
        eventObservationTask = nil
    }
}
