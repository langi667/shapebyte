//
//  LogLevel.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [RTL Tech] on 13.05.24.
//

import Foundation

enum LogLevel: Int, CaseIterable {
    case debug
    case info
    case warning
    case error
    case fatal

    var printableName: String {
        switch self {
        case .debug:
            return "DEBUG"
        case .info:
            return "INFO"
        case .warning:
            return "WARNING"
        case .error:
            return "ERROR"
        case .fatal:
            return "FATAL"
        }
    }
}
