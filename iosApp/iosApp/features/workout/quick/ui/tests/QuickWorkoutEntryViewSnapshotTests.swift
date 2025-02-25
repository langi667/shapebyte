import Testing

@MainActor
struct QuickWorkoutEntryViewSnapshotTests {
    @Test(.tags(.snapshotTest))
    func quickWorkoutEntryViewSnapshots() throws {
        QuickWorkoutEntryView_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
