//
//  SafeArea.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SafeArea {
    static var insets: EdgeInsets {
        let uiInsets = UIApplication
            .shared
            .connectedScenes
            .filter({$0.activationState == .foregroundActive})
            .map({$0 as? UIWindowScene})
            .compactMap({$0})
            .first?.windows.filter({$0.isKeyWindow}).first?.safeAreaInsets ?? .zero

        return EdgeInsets(
            top: uiInsets.top,
            leading: uiInsets.left,
            bottom: uiInsets.bottom,
            trailing: uiInsets.right
        )
    }

    private init() {}
}
