//
//  DefaultExerciseSetCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 29.07.24.
//

import Foundation
import Combine

// TODO: implement correctly by using Repetative
class DefaultExerciseSetCoordinator: ExerciseSetCoordinating {

    fileprivate(set) var statePublisher: PassthroughSubject<ExerciseSetState, Never>
        = PassthroughSubject<ExerciseSetState, Never>()

    func start(set: ExerciseSet) { }

}
