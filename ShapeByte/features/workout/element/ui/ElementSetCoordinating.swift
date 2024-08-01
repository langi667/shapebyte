//
//  SetStateCoordinating.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation
import Combine

protocol ElementSetCoordinating {
    var statePublisher: PassthroughSubject<ElementSet.State, Never> { get }

    func start(set: ElementSet)
}
