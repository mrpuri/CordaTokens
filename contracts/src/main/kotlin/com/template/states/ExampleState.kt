package com.template.states

import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.template.ExampleContract
import com.template.ExampleEvolvableTokenTypeContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party

@BelongsToContract(ExampleContract::class)
class ExampleState (
    val name:String,
    val symbol: String,
    val amount : Long,
    val issuer: Party,
    val viewer: Party,
    override val linearId: UniqueIdentifier= UniqueIdentifier(),
    override val fractionDigits: Int = 0
    ) : EvolvableTokenType() {
        companion object {
            val contractId = this::class.java.enclosingClass.canonicalName
        }

        override val maintainers: List<Party> get() = listOf(issuer, viewer)
}
