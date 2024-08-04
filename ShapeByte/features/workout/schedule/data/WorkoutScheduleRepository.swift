//
//  WorkoutScheduleRepository.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

class WorkoutScheduleRepository {
    private let datasource: WorkoutScheduleDatasource

    init(datasource: WorkoutScheduleDatasource) {
        self.datasource = datasource
    }

    func currentScheduleEntry() -> WorkoutScheduleEntry? {
        return datasource.currentWorkoutScheduleEntry()
    }
}
