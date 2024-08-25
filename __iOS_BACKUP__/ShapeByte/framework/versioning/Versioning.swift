//
//  Versioning.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [Shape Byte Tech] on 14.05.24.
//

import Foundation

protocol Versioning {
    var versionString: String { get }
    var version: Version { get }
}

extension Versioning {
    var version: Version {
        return Version(description: versionString) ?? Version(major: 0, minor: 0, patch: 0)
    }
}
