import Testing

@MainActor
struct AppBarTest {
    @Test(.tags(.snapshotTest))
    func appBarSnapshots() throws {
        AppBar_Previews.snapshots.assertSnapshots(
            as: .image(
                precision: 0.9,
                perceptualPrecision: 0.9
            )
        )
    }
}
