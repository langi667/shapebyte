package de.stefan.lang.foundation.presentation.contract.intent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
public interface UIIntentHandler {
    @OptIn(ExperimentalObjCName::class)
    public fun <T : UIIntent> handleIntent(
        @ObjCName("_")
        event: T,
    )
}
