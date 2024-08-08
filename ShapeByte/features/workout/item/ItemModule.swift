//
//  ItemModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

class ItemModule {
    static private let diModule = DIModule()

    static func timedItemSetsViewModel() -> TimedItemSetsViewModel {
        return TimedItemSetsViewModel(
            logger: SharedModule.logger,
            setsHandler: self.setsHandler()
        )
    }

    static func countdownItemSetsViewModel() -> CountdownItemSetsViewModel {
        return CountdownItemSetsViewModel(
            logger: SharedModule.logger,
            setsHandler: self.setsHandler()
        )
    }

    static func setsHandler() -> ItemSetsHandler {
        return ItemSetsHandler(
            logger: SharedModule.logger,
            timedSetHandler: self.timedSetHandler(),
            defaultSetHandler: self.defaultSetHandler
        )
    }

    private static func timedSetHandler() -> some ItemSetHandling {
        return TimedItemSetHandler(logger: SharedModule.logger)
    }

    private static let defaultSetHandler: some ItemSetHandling = diModule.instanceTypeOrCreate(
        type: DefaultItemSetHandler.self,
        create: {
            DefaultItemSetHandler()
        }
    )

    private init() {}
}
