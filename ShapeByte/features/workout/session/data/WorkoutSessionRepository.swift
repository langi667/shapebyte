//
//  WorkoutSessionRepository.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionRepository {
    let datasource: WorkoutSessionDatasource

    init(datasource: WorkoutSessionDatasource) {
        self.datasource = datasource
    }

    func currentWorkSession() -> WorkoutSession? {
        return datasource.currentWorkSession()
    }
}
