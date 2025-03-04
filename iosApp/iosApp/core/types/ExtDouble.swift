import Foundation

extension Double {
    func toDimension(max: Double? = nil) -> Double {
        if let maxNotNull = max {
            CGFloat(self).toDimension(max: CGFloat(maxNotNull))
        } else {
            CGFloat(self).toDimension(max: nil)
        }
    }

    func toDimensionMax() -> Double {
        let retVal = toDimension(max: self)
        return retVal
    }
}

extension Double {
    func toRadians() -> Double {
        return self * Double.pi / 180
    }
}
