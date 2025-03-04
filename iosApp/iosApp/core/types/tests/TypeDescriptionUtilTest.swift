import Foundation
import Testing

struct TypeDescriptionUtilTest {
    struct TestStruct {}
    class TestClass {}

    struct TypeNameTestData {
        let obj: Any
        let expected: String
    }

    @Test(arguments: [
        TypeNameTestData(obj: NSObject(), expected: "NSObject"),
        TypeNameTestData(obj: TestStruct(), expected: "TestStruct"),
        TypeNameTestData(obj: TestClass(), expected: "TestClass"),
        TypeNameTestData(obj: TypeDescriptionUtil.self, expected: "TypeDescriptionUtil"),
        TypeNameTestData(obj: TypeDescriptionUtil.Type.self, expected: "TypeDescriptionUtil")]
    )
    func classNameTest(testData: TypeNameTestData) async throws {
        let result = TypeDescriptionUtil.typeName(from: testData.obj)
        #expect(testData.expected == result)
    }
}
