//
//  UIState+Data.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 09.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
// TODO: consider testing
extension UIState {
    func viewData<T: AnyObject>() -> T? {
        return (self as? UIStateData<T>)?.data
    }
}
