//
//  CGFloat+Ext.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation

extension CGFloat {
    var integerPart: Int {
        let retVal = floor(self) // Ganzzahlteil
        return Int(retVal)
    }

    var fractionalPart: CGFloat {
        let fractionalPart = self - CGFloat(self.integerPart) // Nachkommastelle
        return fractionalPart
    }
}
