//
//  TypeDescription.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 16.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct TypeDescriptionUtil {
    static func typeName(from obj: Any ) -> String {
        let retVal = String(describing: type(of: obj))
            .replacing(".self", with: "")
            .replacing(".Type", with: "")
            .replacing(".Self", with: "")

        return retVal
    }

}
