import SwiftUI
import PreviewSnapshots

struct HeaderView: View {
    let overlayColor: Color
    let overlayOpacity: Double
    let scale: Double

    let imageScale: Double
    let headerHeight: Double
    let minimumHeaderHeight: Double

    let offsetY: Double
    let contentPaddingVertical: Double
    let contentPaddingHorizontal: Double = .spacingSmall

    init(
        headerHeight: Double = 128,
        minimumHeaderHeight: Double = 102,
        offsetY: Double = 0,
        overlayColor: Color = .SBSecondary,
        overlayOpacity: Double = 1,
        scale: Double = 1,
        imageScale: Double = 1,
        contentPaddingVertical: Double = 20
    ) {
        self.headerHeight = headerHeight
        self.minimumHeaderHeight = minimumHeaderHeight
        self.offsetY = offsetY

        self.overlayColor = overlayColor
        self.overlayOpacity = overlayOpacity

        self.scale = scale
        self.imageScale = imageScale

        self.contentPaddingVertical = contentPaddingVertical
    }

    var body: some View {
        GeometryReader {_ in
            ZStack {
                Rectangle()
                    .fill(overlayColor.opacity(overlayOpacity))

                HStack {
                    VStack(alignment: .leading) {
                        Text("Welcome back")
                            .bodyMedium()
                            .foregroundStyle(Color.white)

                        Text("Stefan")
                            .headlineMedium()
                            .foregroundStyle(Color.white)
                    }
                    .scaleEffect(scale, anchor: .leading)

                    Spacer()

                    Image("Logo")
                        .resizable()
                        .frame(width: .spacingXLarge * imageScale,
                               height: .spacingXLarge * imageScale)
                        .clipShape(Circle())

                }.padding(.horizontal, contentPaddingHorizontal)
                    .padding(.top, contentPaddingVertical)
            }
            .frame(
                height: (headerHeight + offsetY) < minimumHeaderHeight ? minimumHeaderHeight : (headerHeight + offsetY),
                alignment: .bottom)
            .offset(y: -offsetY)
        }.frame(height: headerHeight)
    }
}

struct HeaderView_Previews: PreviewProvider {
    struct Data {
        let headerHeight: Double
        let overlayColor: Color
        let overlayOpacity: Double
        let scale: Double
        let imageScale: Double

        init(
            headerHeight: Double = 128,
            overlayColor: Color = .SBSecondary,
            overlayOpacity: Double = 0,
            scale: Double = 1,
            imageScale: Double = 1
        ) {
            self.headerHeight = headerHeight
            self.overlayColor = overlayColor
            self.overlayOpacity = overlayOpacity
            self.scale = scale
            self.imageScale = imageScale
        }
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<HeaderView_Previews.Data> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "State Expanded",
                    state: .init()
                ),

                    .init(
                        name: "Half shrinked",
                        state: Data(
                            headerHeight: 115,
                            overlayOpacity: 0.5,
                            scale: 0.5,
                            imageScale: 0.75
                        )
                    ),

                    .init(
                        name: "Fully shrinked",
                        state: .init(
                            headerHeight: 102,
                            overlayOpacity: 1,
                            scale: 0,
                            imageScale: 0.5
                        )
                    )
            ],

            configure: { state in
                ZStack(alignment: .topLeading) {
                    BackgroundView()
                    HeaderView(
                        headerHeight: 128,
                        minimumHeaderHeight: 128,
                        offsetY: 0,
                        overlayColor: state.overlayColor,
                        overlayOpacity: state.overlayOpacity,
                        scale: state.scale,
                        imageScale: state.imageScale,
                        contentPaddingVertical: 64
                    ).snapshotSetup()
                }.edgesIgnoringSafeArea(.all)
            }
        )
    }
}
