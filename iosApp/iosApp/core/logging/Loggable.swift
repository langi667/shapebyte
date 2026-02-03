import Foundation
import shared

protocol Loggable {
    var logger: any Logger { get }
    var tag: String { get }

    func logD(message: String)
    func logI(message: String)
    func logW(message: String)
    func logE(message: String)
}

extension Loggable {
    var logger: any Logger { SharedModule.shared.logger()}
    var tag: String { TypeDescriptionUtil.typeName(from: self) }

    func logD(message: String) {
        logger.d(tag: tag, message: message)
    }

    func logI(message: String) {
        logger.i(tag: tag, message: message)
    }

    func logW(message: String) {
        logger.w(tag: tag, message: message)
    }

    func logE(message: String) {
        logger.e(tag: tag, message: message)
    }
}
