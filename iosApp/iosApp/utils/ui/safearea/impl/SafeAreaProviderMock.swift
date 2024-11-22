//
//  File.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 21.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@MainActor
class SafeAreaProviderMock: SafeAreaProviding, Loggable {
    fileprivate(set) var insets: EdgeInsets = EdgeInsets(top: 59, leading: 0, bottom: 34, trailing: 0)
    func detectSafeArea() async { /* NO OP */ }
}
