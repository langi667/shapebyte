import SwiftUI

extension View {
    func addLifecycleReceiver(_ receiver: ViewLifeCycleReceiver) -> some View {
        self
        .onAppear { receiver.onViewAppeared() }
        .onDisappear { receiver.onViewDisappeared() }
    }
}
