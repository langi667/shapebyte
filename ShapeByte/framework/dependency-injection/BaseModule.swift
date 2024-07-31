//
//  GraphQLSourceJoiningProvider.swift
//  GraphQLFragmentJoiner
//
//  Created by Lang, Stefan [Shape Byte Tech] on 30.03.24.
//

import Foundation

open class BaseModule {
    private var instances: [String: Any] = [:]

    func instanceFor<T>(type: T.Type) -> T? {
        let retVal: T?
        if let instance = instances[String(describing: type)] as? T {
            retVal = instance
        } else {
            retVal = nil
        }

        return retVal
    }

    func instanceTypeOrCreate<Base>(type: Base.Type, create: () -> Base) -> Base {
        instanceTypeOrCreate(name: String(describing: type), create: create)
    }

    // TODO: test

    func instanceTypeOrCreate<Base>(name: String, create: () -> Base) -> Base {
        let retVal: Base
        if let instance = instances[name] as? Base {
            retVal = instance
        } else {
            retVal = create()
            register(instance: retVal)
        }

        return retVal
    }

    func register<T>(instance: T) {
        register(
            instance: instance,
            name: String(describing: T.self)
        )
    }

    func register<T>(instance: T, name: String) {
        instances[name] = instance
    }

    func register<T, Base>(instance: T, for type: Base.Type) {
        instances[String(describing: Base.self)] = instance
    }
}
