//
//  Result+ExtensionsTest.swift
//  GraphQLFragmentJoinerTests
//
//  Created by Lang, Stefan [RTL Tech] on 21.03.24.
//

import XCTest

final class Result_ExtensionsTest: XCTest {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testIsFailureShouldBeTrue() throws {
        let sut: Result<Int, Error> = .failure(NSError(domain: "", code: 0, userInfo: nil))
        let result = sut.isFailure()

        XCTAssertTrue(result)
    }

    func testIsFailureShouldBeFalse() throws {
        let sut: Result<Int, Error> = .success(42)
        let result = sut.isFailure()

        XCTAssertFalse(result)
    }

    func testIsSuccessShouldBeTrue() throws {
        let sut: Result<Int, Error> = .success(42)
        let result = sut.isSuccess()

        XCTAssertTrue(result)
    }

    func testIsSuccessShouldBeFalse() throws {
        let sut: Result<Int, Error> = .failure(NSError(domain: "", code: 0, userInfo: nil))
        let result = sut.isSuccess()

        XCTAssertFalse(result)
    }

    func testErrorOrNilShouldReturnError() throws {
        let expected = NSError(domain: "Test", code: 1, userInfo: nil)
        let sut: Result<Int, Error> = .failure(expected)
        let result = sut.errorOrNil()! as NSError

        XCTAssertEqual(expected, result)
    }

    func testErrorOrNilShouldReturnNil() throws {
        let sut: Result<Int, Error> = .success(42)
        let result = sut.errorOrNil()

        XCTAssertNil(result)
    }
}
