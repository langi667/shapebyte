//
//  View+If.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 12.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

extension View {
    @ViewBuilder
    func `if`<Content: View>(_ condition: Bool, transform: (Self) -> Content) -> some View {
        if condition {
            transform(self)
        } else {
            self
        }
    }
}
