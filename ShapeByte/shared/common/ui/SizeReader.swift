//
//  SizeReader.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import SwiftUI

struct SizeReader: ViewModifier {
    @Binding var size: CGSize // Binding für die Größe
    @State var consumed: Bool = false

    func body(content: Content) -> some View {
        content // Der eigentliche Inhalt
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            self.size = geometry.size // Größe setzen
                            DefaultLogger().logInfo("Size: \(size)")
                        }
                }
                .hidden() // Verstecke den GeometryReader
            ).onAppear(perform: {
                self.consumed = true
            })
    }
}

struct Nothing: ViewModifier {
    func body(content: Content) -> some View {
        content
    }
}

extension View {
    func sizeReader(size: Binding<CGSize>) -> some View {
        self.modifier(SizeReader(size: size))
    }
}
