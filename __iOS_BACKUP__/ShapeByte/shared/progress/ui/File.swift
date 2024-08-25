//
//  File.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 30.07.24.
//

import SwiftUI

struct CurvedLine: Shape {
    // Struktur, die das Shape-Protokoll implementiert

    private var angle: CGFloat = 90.0
    private var radius: CGFloat = 90.0

    func path(in rect: CGRect) -> Path {
        let width = 4.0

        var path = Path()
        var startPoint = CGPoint(x: rect.midX, y: rect.midY)

        // Outer
        path.move(to: startPoint)
//        path.addArc(center: startPoint,
//                    radius: rect.midX,
//                    startAngle: .degrees(270), // Beginnt auf der linken Seite
//                    endAngle: .degrees(angle), // Geht zur rechten Seite
//                    clockwise: false)

        let nextPoint = CGPoint(x: startPoint.x, y: rect.minY)
        path.addLine(to: CGPoint(x: nextPoint.x, y: nextPoint.y + width))

        path.addArc(center: CGPoint(x: startPoint.x, y: startPoint.y ),
                    radius: rect.midX - width,
                    startAngle: .degrees(270), // Beginnt auf der linken Seite
                    endAngle: .degrees(angle), // Geht zur rechten Seite
                    clockwise: false)

        startPoint = CGPoint(x: rect.midX, y: rect.maxY - width)
        let endPoint = CGPoint(x: rect.midX, y: rect.maxY)

        // Mittelpunkt für den Halbkreis
        let center = CGPoint(x: rect.midX - (width / 2.0), y: rect.maxY - (width / 2.0))

        // Der Bogen wird vom Startpunkt zum Endpunkt gezeichnet
        path.addQuadCurve(to: endPoint, control: center)

        return path
    }
}

#Preview {
    HStack(alignment: .center) {
        CurvedLine()
            .stroke(Color.blue, lineWidth: 8)
            // .fill()

            .frame(width: 300, height: 300)
            .padding()
    }
}
