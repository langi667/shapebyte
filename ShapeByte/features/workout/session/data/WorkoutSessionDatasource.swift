//
//  WorkoutSessionDatasource.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

protocol WorkoutSessionDatasource {
    func currentWorkSession() -> WorkoutSession?
}
