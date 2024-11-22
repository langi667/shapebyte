//
//  SafeAreaProvider.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@MainActor
class SafeAreaProvider: SafeAreaProviding, Loggable {
    static let shared = SafeAreaProvider()
    fileprivate(set) var insets: EdgeInsets = .init()

    func detectSafeArea() async {
        // already detecting
        if insets != EdgeInsets() {
            logD(message: "Safe area already detected with \(insets)")
            return
        }

        var tryCount = 0

        repeat {
            if let uiInsets = UIApplication
                .shared
                .connectedScenes
                .filter({$0.activationState == .foregroundActive})
                .map({$0 as? UIWindowScene})
                .compactMap({$0})
                .first?.windows.filter({$0.isKeyWindow}).first?.safeAreaInsets {

                insets = EdgeInsets(
                    top: uiInsets.top,
                    leading: uiInsets.left,
                    bottom: uiInsets.bottom,
                    trailing: uiInsets.right
                )

                break
            }

            try? await Task.sleep(nanoseconds: 200_000_000)
            tryCount += 1

        } while (tryCount < 10)

        logW(message: "Unable to determine Safe Area :/ ")
    }

    private init() {}
}
