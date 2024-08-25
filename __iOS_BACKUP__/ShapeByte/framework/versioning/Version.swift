//
//  Version.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [Shape Byte Tech] on 14.05.24.
//

import Foundation

struct Version: Equatable, CustomStringConvertible, Codable {
    let major: Int
    let minor: Int
    let patch: Int

    var description: String {
        return "\(major).\(minor).\(patch)"
    }

    private static func isValidVersion(_ string: String) -> Bool {
        let pattern = "^[0-9]+[0-9.]*$"
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(location: 0, length: string.utf16.count)
        let retVal = regex?.firstMatch(in: string, options: [], range: range) != nil

        return retVal
    }

    init(major: Int, minor: Int, patch: Int) {
        self.major = major
        self.minor = minor
        self.patch = patch
    }

    init?(description: String) {
        if !Self.isValidVersion(description) {
            return nil
        }

        let components = description.split(separator: ".")

        if components.isEmpty {
            return nil
        }

        var major: Int = 0
        var minor: Int = 0
        var patch: Int = 0

        for (index, item) in components.enumerated() {
            if index == 0 {
                major = Int(item) ?? 0
            } else if index == 1 {
                minor = Int(item) ?? 0
            } else if index == 2 {
                patch = Int(item) ?? 0
            }
        }

        self.major = major
        self.minor = minor
        self.patch = patch
    }

    private func toNumber() -> Int {
        return major * 10000 + minor * 100 + patch
    }

    static func < (lhs: Version, rhs: Version) -> Bool {
        let retVal = lhs.toNumber() < rhs.toNumber()
        return retVal
    }

    static func > (lhs: Version, rhs: Version) -> Bool {
        let retVal = lhs.toNumber() > rhs.toNumber()
        return retVal
    }
}
