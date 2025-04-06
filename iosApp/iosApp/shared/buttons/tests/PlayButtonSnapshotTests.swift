import Testing

@MainActor
struct PlayButtonSnapshotTests {
    @Test(.tags(.snapshotTest))
    func playButtonSnapshots() throws {
        PlayButton_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
