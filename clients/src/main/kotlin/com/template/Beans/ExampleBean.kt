package com.template.Beans

import net.corda.core.identity.Party

class ExampleBean (
    val name:String,
    val symbol: String,
    val amount : Long,
    val issuer: String,
    val viewer: String
){}
