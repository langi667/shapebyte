import Testing
import PreviewSnapshotsTesting

@MainActor
struct HomeRootViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func homeRootViewSnapshotTests() throws {
        HomeRootView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
