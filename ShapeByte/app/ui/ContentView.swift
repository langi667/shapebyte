//
//  ContentView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 23.07.24.
//

import SwiftUI
import SwiftData

struct ContentView: View {
    @Environment(\.modelContext) private var modelContext
    @Query private var items: [Item]

    @State
    var viewModel: TimedExerciseSetsViewModel = ExerciseSetsModule
        .timedExerciseSetsViewModel(
            with:
                ExerciseSets(
                    sets: [
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 2),
                        ExerciseSet.timed(duration: 3),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 3),
                        ExerciseSet.timed(duration: 10),
                        ExerciseSet.timed(duration: 8)
                    ]
                )
        )

    var body: some View {

//        ZStack {
//            Theme.Colors.backgroundColor
//            RotatingProgressRing(duration: 2, lineWidth: 20)
//                .padding(35)
//        }

        ZStack {
            Theme.Colors.backgroundColor
            TimedExerciseSetsView(viewModel: viewModel)
        }

    }

    private func addItem() {
        withAnimation {
            let newItem = Item(timestamp: Date())
            modelContext.insert(newItem)
        }
    }

    private func deleteItems(offsets: IndexSet) {
        withAnimation {
            for index in offsets {
                modelContext.delete(items[index])
            }
        }
    }
}

#Preview {
    ContentView()
        .modelContainer(for: Item.self, inMemory: true)
}
