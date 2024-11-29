//
//  View+Preview.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 15.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

extension View {
    var isInPreview: Bool {
        return ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }
}
