//
//  Streak.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 04.08.24.
//

import Foundation

struct Streak {

    let minDays: Int = 5 // Mindestanzahl an Tagen, die getrackt werden müssen
    let startDate: Date // Sonntag der Vorwoche
    let endDate: Date   // Samstag der aktuellen Woche

    let workoutDaysTracked: [Date] // Liste der Tage, an denen Workouts getrackt wurden
    let isFinishable: Bool = true // TODO: implement

    var isFinished: Bool {
        return workoutDaysTracked.count >= minDays // Überprüfe, ob mindestens 5 Tage getrackt wurden
    }

    var incompleteDays: Int {
        return minDays - workoutDaysTracked.count // Gibt die Anzahl der fehlenden Tage zurück
    }

    init(startDate: Date, endDate: Date, workoutDaysTracked: [Date]) {
        self.startDate = startDate
        self.endDate = endDate
        self.workoutDaysTracked = workoutDaysTracked
    }
}
