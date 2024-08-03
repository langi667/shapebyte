//
//  SizeReader.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import SwiftUI

struct SizeReader: ViewModifier {
    @Binding var size: CGSize

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
            )
    }
}

extension View {
    func sizeReader(size: Binding<CGSize>) -> some View {
        self.modifier(SizeReader(size: size))
    }
}

struct PositionReader: ViewModifier {
    @Binding var yPos: CGFloat

    func body(content: Content) -> some View {
        content
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            self.yPos = geometry.frame(in: .global).minY
                        }
                }
                .hidden()
            )

    }
}

extension View {
    func positionReader(yPos: Binding<CGFloat>) -> some View {
        self.modifier(PositionReader(yPos: yPos))
    }
}
