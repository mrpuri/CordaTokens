package com.template.states

import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.template.ExampleContract
import com.template.ExampleEvolvableTokenTypeContract
import com.template.TokenContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party

@BelongsToContract(TokenContract::class)
class TokenIssue (
    val evolvableTokenId: String,
    val amount: Long,
    val recipient: Party,
    override val linearId: UniqueIdentifier= UniqueIdentifier(),
    override val fractionDigits: Int = 0
    ) : EvolvableTokenType() {
        companion object {
            val contractId = this::class.java.enclosingClass.canonicalName
        }

        override val maintainers: List<Party> get() = listOf(recipient)
}
