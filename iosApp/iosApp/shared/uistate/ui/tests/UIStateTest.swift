import Foundation
import Testing
import shared
struct UIStateTest {

    struct TestData {
        let state: UIState
        let data: AnyObject?
    }

    static let uiStateTestData: AnyObject = NSObject()

    @Test(
        arguments: [
            TestData(state: UIState.Idle.shared, data: nil),
            TestData(state: UIState.Loading.shared, data: nil),
            TestData(state: UIStateData(data: uiStateTestData), data: uiStateTestData)
        ]
    )
    func viewDataTest(testData: TestData) async throws {
        #expect(testData.data === testData.state.viewData())
    }

}
