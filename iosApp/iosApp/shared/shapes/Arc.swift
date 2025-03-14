import SwiftUI

struct Arc: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.minX, y: rect.maxY))

        path.addQuadCurve(
            to: CGPoint(x: rect.maxX, y: rect.maxY),
            control: CGPoint(x: rect.midX, y: rect.minY))

        path.closeSubpath()
        return path
    }
}

#Preview {
    VStack {
        Arc()
            .fill(Color.blue)
            .frame(width: 400, height: 200)
    }
}
