//
//  String+ExtensionsTest.swift
//  GraphQLFragmentJoinerTests
//
//  Created by Lang, Stefan [Shape Byte Tech] on 21.03.24.
//

import XCTest

final class String_ExtensionsTest: XCTest {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testTrimShouldNotChangeString() throws {
        let sut = "Tailor Swift"
        let result = sut.trimmed()
        let expected = "Tailor Swift"

        XCTAssertEqual(expected, result)
    }

    func testTrimShouldRemoveWhitespaceInFront() throws {
        let sut = "  Tailor Swift"
        let result = sut.trimmed()
        let expected = "Tailor Swift"

        XCTAssertEqual(expected, result)
    }

    func testTrimShouldRemoveWhitespaceInBack() throws {
        let sut = "Tailor Swift  "
        let result = sut.trimmed()
        let expected = "Tailor Swift"

        XCTAssertEqual(expected, result)
    }

    func testTrimShouldRemoveWhitespaceInFrontAndBack() throws {
        let sut = " Tailor Swift  "
        let result = sut.trimmed()
        let expected = "Tailor Swift"

        XCTAssertEqual(expected, result)
    }

    func testTrimShouldRemoveNewlineInFrontAndBack() throws {
        let sut = "\n\n Tailor Swift \n "
        let result = sut.trimmed()
        let expected = "Tailor Swift"

        XCTAssertEqual(expected, result)
    }

    func testIsBlankShouldBeTrue() throws {
        var sut = ""
        XCTAssertTrue(sut.isBlank)

        sut = "  "
        XCTAssertTrue(sut.isBlank)

        sut = "\n"
        XCTAssertTrue(sut.isBlank)

        sut = " \n"
        XCTAssertTrue(sut.isBlank)

        sut = "  \n \n"
        XCTAssertTrue(sut.isBlank)
    }

    func testIsBlankShouldBeFalse() throws {
        var sut = "Test"
        XCTAssertFalse(sut.isBlank)

        sut = "  Test"
        XCTAssertFalse(sut.isBlank)

        sut = "Test\n"
        XCTAssertFalse(sut.isBlank)

        sut = "\nTest\n"
        XCTAssertFalse(sut.isBlank)

        sut = " # \n \n"
        XCTAssertFalse(sut.isBlank)

        sut = " \n . \n   "
        XCTAssertFalse(sut.isBlank)
    }

    func testDropFirstLineShouldDoNothingOnBlankString() throws {
        var sut = ""
        var result = sut.dropFirstLine()
        XCTAssertEqual(sut, result)

        sut = "  "
        result = sut.dropFirstLine()
        XCTAssertEqual(sut, result)
    }

    func testDropFirstLineShouldCauseBlankString() throws {
        var sut = "\n"
        var result = sut.dropFirstLine()
        XCTAssertEqual("", result)

        sut = "\n  "
        result = sut.dropFirstLine()
        XCTAssertEqual("  ", result)
    }

    func testDropFirstLineShouldWorkCorrectly() throws {
        let sut = """
                  Erste Zeile
                  Zweite Zeile
                  Dritte Zeile
                  Vierte Zeile
                  """

        let expected = """
                  Zweite Zeile
                  Dritte Zeile
                  Vierte Zeile
                  """

        let result = sut.dropFirstLine()
        XCTAssertEqual(expected, result)
    }

    func testDropLastLineShouldDoNothingOnBlankString() throws {
        var sut = ""
        var result = sut.dropLastLine()
        XCTAssertEqual(sut, result)

        sut = "  "
        result = sut.dropFirstLine()
        XCTAssertEqual(sut, result)
    }

    func testDropLastLineShouldCauseBlankString() throws {
        var sut = "\n"
        var result = sut.dropLastLine()
        XCTAssertEqual("", result)

        sut = "\n  "
        result = sut.dropLastLine()
        XCTAssertEqual("", result)
    }

    func testDropLastLineShouldWorkCorrectly() throws {
        let sut = """
                  Erste Zeile
                  Zweite Zeile
                  Dritte Zeile
                  Vierte Zeile
                  """

        let expected = """
                  Erste Zeile
                  Zweite Zeile
                  Dritte Zeile
                  """

        let result = sut.dropLastLine()
        XCTAssertEqual(expected, result)
    }

    func testDropFirstAndLastLineShouldWorkCorrectly() throws {
        var sut = """
                  Erste Zeile
                  Zweite Zeile
                  Dritte Zeile
                  Vierte Zeile
                  """

        var expected = """
                  Zweite Zeile
                  Dritte Zeile
                  """

        var result = sut.dropFirstAndLastLine()
        XCTAssertEqual(expected, result)

        sut = "Erste Zeile\nZweite Zeile"

        expected = """
                   """

        result = sut.dropFirstAndLastLine()
        XCTAssertEqual(expected, result)
    }

    func testCharacterCountShouldFindCharacters() throws {
        var sut = """
                  abc
                  """
        var result = sut.characterCount("a")
        XCTAssertEqual(1, result)

        sut = """
                  abcA test it a
                  """
        result = sut.characterCount("a")
        XCTAssertEqual(2, result)
    }

    func testCharacterCountShouldNotFindCharacters() throws {
        var sut = """
                  abc
                  """
        var result = sut.characterCount("d")
        XCTAssertEqual(0, result)

        sut = """
                  abcA test it a
                  """
        result = sut.characterCount("B")
        XCTAssertEqual(0, result)
    }
}
