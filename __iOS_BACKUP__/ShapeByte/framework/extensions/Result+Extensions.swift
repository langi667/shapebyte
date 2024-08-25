//
//  Result+Extensions.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [Shape Byte Tech] on 21.03.24.
//

import Foundation

extension Result {
    func isSuccess() -> Bool {
        if case .failure = self {
            return false
        } else {
            return true
        }
    }

    func isFailure() -> Bool {
        return !isSuccess()
    }

    func errorOrNil() -> Error? {
        if case let .failure(error) = self {
            return error
        } else {
            return nil
        }
    }

    func toErrorOrNil<T: Error>() -> T? {
        if case let .failure(error) = self {
            return error as? T
        } else {
            return nil
        }
    }

}
