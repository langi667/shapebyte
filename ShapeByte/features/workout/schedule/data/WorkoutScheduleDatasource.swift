//
//  WorkoutScheduleDatasource.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import Foundation

protocol WorkoutScheduleDatasource {
    func currentWorkoutScheduleEntry() -> WorkoutScheduleEntry?
}
