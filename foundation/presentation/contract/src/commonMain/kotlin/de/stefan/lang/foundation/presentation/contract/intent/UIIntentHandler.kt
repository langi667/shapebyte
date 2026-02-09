package de.stefan.lang.foundation.presentation.contract.intent

public interface UIIntentHandler {
    public fun <T: UIIntent> handleIntent(event: T)
}