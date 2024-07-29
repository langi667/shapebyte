//
//  SetStateCoordinating.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation
import Combine

protocol ExerciseSetCoordinating {
    var statePublisher: PassthroughSubject<ExerciseSetState, Never> { get }

    func start(set: ExerciseSet)
}
