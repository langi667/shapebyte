//
//  SizeReader.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import SwiftUI

struct SizeReader: ViewModifier {
    @Binding var size: CGSize
    @State var consumed: Bool = false

    func body(content: Content) -> some View {
        content
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            self.size = geometry.size
                        }
                }
                .hidden()
            ).onAppear(perform: {
                self.consumed = true
            }
        )
    }
}

extension View {
    func sizeReader(size: Binding<CGSize>) -> some View {
        self.modifier(SizeReader(size: size))
    }
}
