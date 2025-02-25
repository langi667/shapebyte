import Testing
import PreviewSnapshotsTesting

@MainActor
struct BackgroundViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func backgroundView_Snapshots() throws {
        BackgroundView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
