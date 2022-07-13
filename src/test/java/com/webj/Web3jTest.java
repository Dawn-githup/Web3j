package com.webj;

import com.utils.Environment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * 1. @description:
 * 2. @author: Dawn
 * 3. @time: 2022/6/30
 */
public class Web3jTest {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            Web3j client = Web3j.build(new HttpService(Environment.RPC_URL));
            EthGetBalance ethGetBalance = client.ethGetBalance(Environment.From_Eth_address,
                    DefaultBlockParameterName.fromString(DefaultBlockParameterName.LATEST.name())
            ).sendAsync().get();
            BigInteger balance = ethGetBalance.getBalance();
            System.out.println(ethGetBalance.getBalance());
            // => 741270235881990866
        }
}

