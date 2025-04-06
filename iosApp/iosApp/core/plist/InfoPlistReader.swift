import Foundation

class InfoPlistReader {
    lazy var appVersion = plistStringValue(forKey: "CFBundleShortVersionString", fallback: "")
    lazy var bundleIdentifier = plistStringValue(forKey: "CFBundleIdentifier", fallback: "")
    lazy var buildVersion = plistIntValue(forKey: "CFBundleVersion", fallback: 0)

    func plistValue(forKey key: String) -> Any? {
        return Bundle.main.object(forInfoDictionaryKey: key)
    }

    func plistIntValue(forKey key: String) -> Int? {
        let valueRaw = Bundle.main.object(forInfoDictionaryKey: key)

        if let intValue = valueRaw as? Int {
            return intValue
        } else if let stringValue = valueRaw as? String {
            return Int(stringValue)
        }

        return nil
    }

    func plistIntValue(forKey key: String, fallback: Int) -> Int {
        return plistIntValue(forKey: key) ?? fallback
    }

    func plistStringValue(forKey key: String) -> String? {
        return plistValue(forKey: key) as? String
    }

    func plistStringValue(forKey key: String, fallback: String) -> String {
        let value = plistStringValue(forKey: key) ?? fallback
        return value
    }
}
