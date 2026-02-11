import Testing
import SwiftUI
import ViewInspector

class TestReceiver: ViewLifeCycleReceiver {
    var didAppear = false
    var didDisappear = false

    func onViewAppeared() { didAppear = true }
    func onViewDisappeared() { didDisappear = true }
}

@Suite("Lifecycle Extension Test")
struct LifecycleExtensionTests {
    @Test
    @MainActor
    func testOnAppearAndDisappear() async throws {
        let receiver = TestReceiver()
        let sut = Color.red.addLifecycleReceiver(receiver)

        _ = try sut.inspect().callOnAppear()
        #expect(receiver.didAppear)

        _ = try sut.inspect().callOnDisappear()
        #expect(receiver.didDisappear)
    }
}
