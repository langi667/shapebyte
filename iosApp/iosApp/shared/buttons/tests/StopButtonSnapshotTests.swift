import Testing

@MainActor
struct StopButtonSnapshotTests {
    @Test(.tags(.snapshotTest))
    func stopButtonSnapshots() throws {
        StopButton_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
