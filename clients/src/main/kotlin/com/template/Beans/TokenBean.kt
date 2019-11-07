package com.template.Beans

import net.corda.core.identity.Party

class TokenBean (
    val evolvableTokenId: String,
    val amount: Long,
    val recipient: String
){}
