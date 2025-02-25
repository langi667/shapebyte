import Foundation
import shared
// TODO: move to shared

extension CGFloat {
    func toDimension(max: CGFloat? = nil) -> CGFloat {
        let dimensionProvider = SharedModule.shared.dimensionProvider()
        let retVal: Float

        if let maxNotNull = max {
            retVal = dimensionProvider.withDimensionalAspect(
                height: Float(self),
                max: Float(maxNotNull)
            )
        } else {
            retVal = dimensionProvider.withDimensionalAspect(
                height: Float(self)
            )
        }

        return CGFloat(retVal)
    }

    func toDimensionMax() -> CGFloat {
        let retVal = toDimension(max: self)
        return retVal
    }
}
