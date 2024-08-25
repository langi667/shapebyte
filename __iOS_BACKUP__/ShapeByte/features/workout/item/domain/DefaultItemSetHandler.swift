//
//  DefaultItemSetHandler.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation
import Combine

// TODO: implement correctly by using Repetative
class DefaultItemSetHandler: ItemSetHandling {

    fileprivate(set) var statePublisher: PassthroughSubject<ItemSet.State, Never>
        = PassthroughSubject<ItemSet.State, Never>()

    func start(set: ItemSet) { }

    // TODO: implement
    func pause() {

    }

}
