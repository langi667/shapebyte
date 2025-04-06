import Testing
import shared

struct SpacingTest {

    @Test
    func initWithInt() async throws {
        let sut = Spacings(
            xTiny: 1,
            tiny: 2,
            small: 3,
            medium: 4,
            large: 5,
            xLarge: 6,
            xxLarge: 7,
            xxxLarge: 8
        )

        #expect(sut.xTiny == 1)
        #expect(sut.tiny == 2)
        #expect(sut.small == 3)

        #expect(sut.medium == 4)
        #expect(sut.large == 5)
        #expect(sut.xLarge == 6)

        #expect(sut.xxLarge == 7)
        #expect(sut.xxxLarge == 8)
    }

    @Test
    func initWithSharedSpacing() async throws {
        let sharedSpacing = shared.Spacings(
            xTiny: 1,
            tiny: 2,
            small: 3,
            medium: 4,
            large: 5,
            xLarge: 6,
            xxLarge: 7,
            xxxLarge: 8
        )

        let sut = Spacings(sharedSpacing)

        #expect(sut.xTiny == 1)
        #expect(sut.tiny == 2)
        #expect(sut.small == 3)

        #expect(sut.medium == 4)
        #expect(sut.large == 5)
        #expect(sut.xLarge == 6)

        #expect(sut.xxLarge == 7)
        #expect(sut.xxxLarge == 8)
    }

}
