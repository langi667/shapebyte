import Foundation
import shared

extension ItemSetTimed {
    static func forDuration(_ duration: Duration) -> ItemSetTimed {
        let components = duration.components
        let seconds = components.seconds
        let milliseconds = components.attoseconds / 1_000_000_000_000_000
        let total = ((seconds * 1000) + milliseconds )

        let itemSet = ItemSetTimedMilliseconds(milliSecsRaw: Int32(seconds))
        return itemSet
    }
}
