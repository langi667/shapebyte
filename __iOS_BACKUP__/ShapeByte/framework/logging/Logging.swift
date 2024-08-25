//
//  Logging.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [Shape Byte Tech] on 13.05.24.
//

import Foundation

protocol Logging {
    var isEnabled: Bool { get set }
    var logLevel: LogLevel { get set }

    func log(_ message: String, level: LogLevel) -> String
}

extension Logging {

    @discardableResult
    func logDebug(obj: Any) -> String {
        return log(String(describing: obj), level: .debug)
    }

    @discardableResult
    func logDebug(_ message: String) -> String {
        return log(message, level: .debug)
    }

    @discardableResult
    func logInfo(_ message: String) -> String {
        return log(message, level: .info)
    }

    @discardableResult
    func logWarning(_ message: String) -> String {
        return log(message, level: .warning)
    }

    @discardableResult
    func logError(_ message: String) -> String {
        return log(message, level: .error)
    }

    @discardableResult
    func logFatal(_ message: String) -> String {
        return log(message, level: .fatal)
    }
}
