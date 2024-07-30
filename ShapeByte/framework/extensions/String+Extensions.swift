//
//  String+Extensions.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [RTL Tech] on 21.03.24.
//

import Foundation

extension String {
    func trimmed() -> String {
        let retVal = self.trimmingCharacters(in: .whitespacesAndNewlines)
        return retVal
    }

    var isBlank: Bool {
        get {
            let retVal = self.trimmed().isEmpty
            return retVal
        }
    }

    func dropFirstLine() -> String {
        let lines = self.components(separatedBy: "\n")

        if lines.count == 1 {
            return self
        }

        let retVal = lines.dropFirst().joined(separator: "\n")
        return retVal
    }

    func dropLastLine() -> String {
        let lines = self.components(separatedBy: "\n")

        if lines.count == 1 {
            return self
        }

        let retVal = lines.dropLast().joined(separator: "\n")
        return retVal
    }

    func dropFirstAndLastLine() -> String {
        let lines = self.components(separatedBy: "\n")
        let retVal = lines.dropFirst().dropLast().joined(separator: "\n")

        return retVal
    }

    func characterCount(_ character: Character) -> Int {
        let retVal = self.filter { $0 == character }.count
        return retVal
    }
}
