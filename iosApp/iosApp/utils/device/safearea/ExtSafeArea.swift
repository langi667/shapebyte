//
//  SafeArea.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 25.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SafeArea {
    var insets: EdgeInsets {
        EdgeInsets(
            top: self.top,
            leading: self.leading,
            bottom: self.bottom,
            trailing: self.trailing
        )
    }
}
