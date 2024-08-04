//
//  SafeAreaInsets.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 04.08.24.
//

import SwiftUI

struct SafeAreaInsetsKey: EnvironmentKey {
    static let defaultValue: EdgeInsets = EdgeInsets()
}

extension EnvironmentValues {
    var safeAreaInsets: EdgeInsets {
        get { self[SafeAreaInsetsKey.self] }
        set { self[SafeAreaInsetsKey.self] = newValue }
    }
}
