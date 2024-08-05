//
//  ItemSetHandling.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation
import Combine

protocol ItemSetHandling {
    var statePublisher: PassthroughSubject<ItemSet.State, Never> { get }

    func start(set: ItemSet)
}
