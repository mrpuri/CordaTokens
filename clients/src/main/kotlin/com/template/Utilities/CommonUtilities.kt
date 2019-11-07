package com.template.Utilities

import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.template.Beans.ExampleBean
import com.template.Beans.ExampleResponseBean
import com.template.Beans.TokenResponseBean
import com.template.states.ExampleState
import com.template.states.TokenIssue
import net.corda.core.contracts.StateAndRef
import java.util.ArrayList

class CommonUtilities {
    companion object {
        fun convertToResponseBean(list: List<StateAndRef<ExampleState>>): List<ExampleResponseBean> {
            val ExampleResponseBean = ArrayList<ExampleResponseBean>()
            for (i in 0 until list.size) {
                val ExampleBean = list.get(i).state.data

                var res = ExampleResponseBean(ExampleBean.name, ExampleBean.symbol, ExampleBean.amount, ExampleBean.linearId.toString())

                ExampleResponseBean.add(res)
            }
            return ExampleResponseBean
        }

        fun convertToResponse(list: List<StateAndRef<EvolvableTokenType>>): List<TokenResponseBean> {
            val TokenResponseBean = ArrayList<TokenResponseBean>()
            for (i in 0 until list.size) {
                val ExampleBean = list.get(i).state.data
                val newOwner = ExampleBean.maintainers
                println(newOwner)
                var res = TokenResponseBean(ExampleBean.linearId.toString())

                TokenResponseBean.add(res)
            }
            return TokenResponseBean
        }
    }
}

