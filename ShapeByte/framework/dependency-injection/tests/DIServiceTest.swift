//
//  BaseModuleTest.swift
//  GraphQLFragmentJoinerTests
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.04.24.
//

import XCTest

final class DIServiceTest: XCTestCase {

    class TestClass: Equatable {
        let value: Int
        let name: String

        static func == (lhs: DIServiceTest.TestClass, rhs: DIServiceTest.TestClass) -> Bool {
            let retVal = lhs.name == rhs.name && lhs.value == rhs.value
            return retVal
        }

        init(value: Int, name: String) {
            self.value = value
            self.name = name
        }
    }

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testInstanceForTypeOrCreate() throws {
        let sut = DIService()
        let value = 42
        let name = "Test"

        let result = sut.instanceTypeOrCreate(type: TestClass.self, create: { TestClass(value: value, name: name) })
        let expected = TestClass(value: value, name: name)
        XCTAssertEqual(expected, result)

        let result2 = sut.instanceTypeOrCreate(type: TestClass.self, create: { TestClass(value: value, name: name) })

        XCTAssertEqual(expected, result2)
        XCTAssertEqual(result, result2)
        XCTAssertTrue(result === result2) // should not have recreated instance

        // Different creator, instance should still be the same
        let result3 = sut.instanceTypeOrCreate(type: TestClass.self, create: { TestClass(value: value + 1, name: name + "123") })

        XCTAssertEqual(expected, result3)
        XCTAssertEqual(result, result3)
        XCTAssertEqual(result2, result3)
        XCTAssertTrue(result === result3) // should not have recreated instance
        XCTAssertTrue(result2 === result3) // should not have recreated instance

    }

    func testInstanceForType() throws {
        let sut = DIService()
        var result = sut.instanceFor(type: TestClass.self)
        XCTAssertNil(result)

        let value = 42
        let name = "Test"

        result = sut.instanceTypeOrCreate(type: TestClass.self, create: { TestClass(value: value, name: name) })
        XCTAssertNotNil(result)

        let expected = TestClass(value: value, name: name)
        XCTAssertEqual(expected, result)
    }

    func testRegister() throws {
        let sut = DIService()

        var result = sut.instanceFor(type: TestClass.self)
        XCTAssertNil(result)

        let value1 = 42
        let name1 = "Test"

        let value2 = 421
        let name2 = "Test"

        let instance1 = TestClass(value: value1, name: name1)
        sut.register(instance: instance1)
        result = sut.instanceFor(type: TestClass.self)

        XCTAssertNotNil(result)
        XCTAssertEqual(instance1, result)

        let instance2 = TestClass(value: value2, name: name2)
        sut.register(instance: instance2)
        result = sut.instanceFor(type: TestClass.self)

        XCTAssertNotNil(result)
        XCTAssertNotEqual(instance1, result)
        XCTAssertEqual(instance2, result)
    }
}
