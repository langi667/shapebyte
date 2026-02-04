import Foundation
import shared

extension ItemSetTimed {
    static func forDuration(_ duration: Duration) -> ItemSetTimed {
        let components = duration.components
        let seconds = components.seconds
        let milliseconds = components.attoseconds / 1_000_000_000_000_000

        let itemSet = ItemSetTimedMilliseconds(milliSecsRaw: Int32(seconds))
        return itemSet
    }
}
