import Testing
import SwiftUI
import shared

struct FontDescriptorNativeTest {

    @Test("Should map FontDescriptor for System to correct Native font")
    func testNative() async throws {
        let sut = FontDescriptorSystem(size: 17, weight: .bold)
        #expect(Font.system(size: 17, weight: .bold) == sut.font )
    }

}
