package com.webj;

import com.utils.Environment;
import io.reactivex.Flowable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.events.NewHeadsNotification;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * 1. @description:
 * 2. @author: Dawn
 * 3. @time: 2022/7/12
 */
public class newPendingTransactionFilter {
    public static void main(String[] args) throws IOException {
        Web3j client = Web3j.build(new HttpService(Environment.RPC_URL));
        BigInteger filterId = client.ethBlockNumber().send().getBlockNumber();
        System.out.println(filterId.toString());


    }
}
