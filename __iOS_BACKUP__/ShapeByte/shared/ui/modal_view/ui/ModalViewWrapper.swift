//
//  ModalViewWrapper.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import SwiftUI

struct ModalViewWrapper<Content: View>: View {
    @Binding var isPresented: Bool

    let title: String
    let content: Content
    var closeIcon: some View = Image(systemName: "xmark")
        .font(Theme.Fonts.body)
        .tint(Theme.Colors.textLight)

    var body: some View {
        NavigationView {
            ZStack {
                content
            }
            .navigationTitle( Text(title) )
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button(action: {
                        isPresented = false
                    }) {
                        closeIcon
                    }
                }
            }.ignoresSafeArea()
        }
    }

    init(isPresented: Binding<Bool>, title: String, content: Content) {
        self._isPresented = isPresented
        self.title = title
        self.content = content
    }
}
