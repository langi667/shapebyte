//
//  ModalViewWrapper.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 03.08.24.
//

import SwiftUI

struct ModalViewWrapper<Content: View>: View {
    @Binding var isPresented: Bool

    let title: String
    let content: Content
    var closeIcon: some View = Image(systemName: "xmark").font(.title)

    var body: some View {
        NavigationView {
            ZStack {
                content
            }
            .navigationTitle(title)
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
}
