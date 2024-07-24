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
    var progress: CGFloat = 0.0

    var body: some View {

        VStack {

            AnimatedProgressRing(progress: 0.0)
            Button("reset", action: {
                self.progress = 0
            }
            )
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
