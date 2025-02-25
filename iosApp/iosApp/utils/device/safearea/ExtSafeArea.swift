import SwiftUI
import shared

extension SafeArea {
    var insets: EdgeInsets {
        EdgeInsets(
            top: self.top,
            leading: self.leading,
            bottom: self.bottom,
            trailing: self.trailing
        )
    }
}
