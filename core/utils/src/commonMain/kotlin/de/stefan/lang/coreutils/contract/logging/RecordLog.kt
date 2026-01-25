package de.stefan.lang.coreutils.contract.logging

public data class RecordLog(
    val tag: String,
    val level: String,
    val message: String,
)
