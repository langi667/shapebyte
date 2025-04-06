import Testing

@MainActor
struct PauseButtonSnapshotTests {
    @Test(.tags(.snapshotTest))
    func pauseButtonSnapshots() throws {
        PauseButton_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
