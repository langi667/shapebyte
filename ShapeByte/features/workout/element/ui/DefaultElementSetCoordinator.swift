//
//  DefaultElementSetCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation
import Combine

// TODO: implement correctly by using Repetative
class DefaultElementSetCoordinator: ElementSetCoordinating {

    fileprivate(set) var statePublisher: PassthroughSubject<ElementSet.State, Never>
        = PassthroughSubject<ElementSet.State, Never>()

    func start(set: ElementSet) { }

}
