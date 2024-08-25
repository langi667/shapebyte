//
//  WorkoutScheduleDatasource.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

protocol WorkoutScheduleDatasource {
    func currentWorkoutScheduleEntry() -> WorkoutScheduleEntry?
}
