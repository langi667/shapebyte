//
//  SizeReader.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import SwiftUI

struct SizeReader: ViewModifier {
    @Binding var size: CGSize // Binding für die Größe

    func body(content: Content) -> some View {
        content // Der eigentliche Inhalt
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            self.size = geometry.size // Größe setzen
                        }
                }
                    .hidden() // Verstecke den GeometryReader
            )
    }
}

extension View {
    func sizeReader(size: Binding<CGSize>) -> some View {
        self.modifier(SizeReader(size: size))
    }
}
