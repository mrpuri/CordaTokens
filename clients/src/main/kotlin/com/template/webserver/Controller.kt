package com.template.webserver

import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.template.Beans.ExampleBean
import com.template.Beans.ExampleResponseBean
import com.template.Beans.TokenBean
import com.template.Beans.TokenResponseBean
import com.template.Utilities.CommonUtilities
import com.template.flows.CreateExampleToken
import com.template.flows.ExampleFlow
import com.template.states.ExampleState
import com.template.states.TokenIssue
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }
    private val proxy = rpc.proxy


    @PostMapping(value = "/ExampleIssue", produces = arrayOf("text/plain"))
    private fun signedTrade(@RequestBody ExampleBean: ExampleBean): ResponseEntity<String> {


        val Owner = proxy.nodeInfo().legalIdentities.get(0)
        val Recipient = proxy.partiesFromName(ExampleBean.viewer, false).iterator().next()
            ?:throw  IllegalArgumentException("No exact match found for Buyer name ${ExampleBean.viewer}") as Throwable

        val result = proxy.startFlowDynamic(CreateExampleToken::class.java, ExampleBean.name, ExampleBean.symbol,
            ExampleBean.amount, Owner, Recipient).returnValue.get()
        println(result.coreTransaction.outputsOfType(ExampleState::class.java).get(0).linearId.toString())
        return ResponseEntity(HttpStatus.CREATED)
    }





    @GetMapping(value = "/getTokens", produces = arrayOf("application/json"))
    private fun getAllTrades():ResponseEntity<List<ExampleResponseBean>>{
        val List = proxy.vaultQuery(ExampleState::class.java).states
        return ResponseEntity.ok(CommonUtilities.convertToResponseBean(List))
    }


    @PostMapping(value = "/Transfer", produces = arrayOf("text/plain"))
    private fun Trade(@RequestBody TokenBean: TokenBean): ResponseEntity<String> {



        val Owner = proxy.nodeInfo().legalIdentities.get(0)
        val Recipient = proxy.partiesFromName(TokenBean.recipient, false).iterator().next()
            ?:throw  IllegalArgumentException("No exact match found for Buyer name ${TokenBean.recipient}") as Throwable

        val result = proxy.startFlowDynamic(ExampleFlow::class.java, TokenBean.evolvableTokenId,TokenBean.amount, Recipient).returnValue.get()
        //println(result.coreTransaction.outputsOfType(TokenIssue::class.java).get(0).evolvableTokenId)
        return ResponseEntity(HttpStatus.CREATED)
    }


    @GetMapping(value = "/getIssuedTokens", produces = arrayOf("application/json"))
    private fun getIssuedTokens():ResponseEntity<List<TokenResponseBean>>{
        val list = proxy.vaultQuery(EvolvableTokenType::class.java).states


        return ResponseEntity.ok(CommonUtilities.convertToResponse(list))
    }


}
