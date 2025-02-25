import Testing
import PreviewSnapshotsTesting

@MainActor
struct HeaderViewSnapshotTests {

    @Test(.tags(.snapshotTest))
    func testHeaderViewSnapshot() throws {
        HeaderView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.8,
                perceptualPrecision: 0.8
            )
        )
    }
}
