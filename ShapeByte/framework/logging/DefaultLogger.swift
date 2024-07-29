//
//  DefaultLogger.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [RTL Tech] on 13.05.24.
//

import Foundation

class DefaultLogger: Logging {
    var isEnabled: Bool = true
    var logLevel: LogLevel = .debug

    func log(_ message: String, level: LogLevel) -> String {
        if !isEnabled || level.rawValue < logLevel.rawValue {
            return ""
        }

        let levelString = logLevelAsString(level)
        let date = currDateAsString()

        let retVal = "[\(levelString)] \(date): \(message)"
        print(retVal)

        return retVal
    }

    private func logLevelAsString(_ level: LogLevel) -> String {
        let retVal: String = level.printableName
        return retVal
    }

    private func currDateAsString() -> String {
        let date = Date() // Current date and time
        let formatter = DateFormatter()

        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        let retVal = formatter.string(from: date)

        return retVal
    }
}
