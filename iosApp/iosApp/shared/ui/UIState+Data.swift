//
//  UIState+Data.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 09.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared

extension UIState {
    func viewData<T: AnyObject>() -> T? {
        return (self as? UIStateData<T>)?.data
    }
}
