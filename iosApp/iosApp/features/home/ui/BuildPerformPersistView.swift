import SwiftUI
import shared
import PreviewSnapshots

struct BuildPerformPersistViewSettings {
    static let primaryButtonSize: Double = .dimensionLarge
    static let secondaryButtonSize: Double = .dimensionMedium
    static let primaryButtonPadding: Double = .spacingSmall

    static let height = primaryButtonSize + (2 * primaryButtonPadding)
}

struct BuildPerformPersistView: View {
    let primaryButtonSize = BuildPerformPersistViewSettings.primaryButtonSize
    let secondaryButtonSize = BuildPerformPersistViewSettings.secondaryButtonSize
    let secondaryButtonOffset: Double = .dimensionXTiny

    static let buttonPadding = BuildPerformPersistViewSettings.primaryButtonPadding
    static let buttonOffset: Double = Self.buttonPadding + .spacingTiny + .spacingXTiny

    var body: some View {
        ZStack(alignment: Alignment.center) {
            HStack(alignment: .center, spacing: 0) {

                HStack(alignment: .center, spacing: 0) {
                    Spacer()
                    BuildPerformPersistViewImgView(
                        imgName: "plan",
                        size: secondaryButtonSize
                    )
                }
                .frame(width: primaryButtonSize, height: primaryButtonSize).mask(Mask())
                .offset(x: Self.buttonOffset)

                ZStack {
                    BuildPerformPersistViewImgView(
                        imgName: "Logo",
                        size: primaryButtonSize
                    )
                }
                .zIndex(1000)

                HStack(alignment: .center, spacing: 0) {
                    BuildPerformPersistViewImgView(
                        imgName: "persist",
                        size: secondaryButtonSize
                    )
                    Spacer().frame(width: Self.buttonPadding * 2)
                }
                .frame(width: primaryButtonSize, height: primaryButtonSize).mask(Mask().rotation(.degrees(180)))
                .offset(x: -Self.buttonOffset)
            }
        }
    }
}

private struct BuildPerformPersistViewImgView: View {
    let imgName: String
    let size: Double

    var body: some View {
        ZStack {
            Image(imgName)
                .resizable()
                .clipShape(Circle())
        }.frame(
            width: size,
            height: size
        )
    }
}

private struct Mask: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.minX, y: rect.minY))
        path.addLine(to: CGPoint(x: rect.maxX, y: rect.minY))

        let radius = (rect.size.height / 2 )

        path.addLine(to: CGPoint(x: rect.maxX, y: rect.minY))
        path.addArc(
            center: CGPoint(x: rect.maxX + radius / 2, y: rect.midY),
            radius: radius,
            startAngle: .degrees(-90),
            endAngle: .degrees(90),
            clockwise: true
        )

        path.addLine(to: CGPoint(x: rect.maxX, y: rect.maxY))
        path.addLine(to: CGPoint(x: rect.minX, y: rect.maxY))

        path.closeSubpath()
        return path
    }
}

struct BuildPerformPersistView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<None> {
         PreviewSnapshots(
             configurations: [
                .init(name: "Default", state: None())
             ],

             configure: { _ in
                 VStack(alignment: .leading) {
                     BuildPerformPersistView()
                     Spacer()
                 }
             }
         )
     }
}
