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
