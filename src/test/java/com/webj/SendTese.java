package com.webj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.utils.Environment;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

/**
 * 1. @description:
 * 2. @author: Dawn
 * 3. @time: 2022/7/11
 */
public class SendTese {
    public static void main(String[] args) throws Exception {
        Web3j client = Web3j.build(new HttpService(Environment.RPC_URL));
//        Web3j client = Web3j.build(new HttpService("http://192.168.10.232:8545/"));

        // godwoken rpc  https://godwoken-betanet-v1.ckbapp.dev
//        Web3j client = Web3j.build(new HttpService("http://18.162.235.225:8000/ "));

        EthChainId chainId = client.ethChainId().send();
        System.out.println("id: " + chainId.getChainId().toString());


        // 获取 nonce 值
        EthGetTransactionCount ethGetTransactionCount = client
                .ethGetTransactionCount(Environment.From_Eth_address, DefaultBlockParameterName.PENDING)
                .sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println("nonce :" + nonce);

//        BigInteger nonce = BigInteger.valueOf(15);

        // 构建交易
        RawTransaction etherTransaction = RawTransaction.createEtherTransaction(
                nonce,
                client.ethGasPrice().sendAsync().get().getGasPrice(),
                DefaultGasProvider.GAS_LIMIT,Environment.To_Eth_address,
                Convert.toWei("1.122256565656565656565656565", Convert.Unit.ETHER).toBigInteger()
        );

        String jsonString = JSON.toJSONString(etherTransaction);
        System.out.println(jsonString);
        System.out.println(etherTransaction);

        // 加载私钥
        Credentials credentials = Credentials.create(Environment.Private_Key);

        // 使用私钥签名交易并发送
//        byte[] signature = TransactionEncoder.signMessage(etherTransaction, chainId, credentials);
        byte[] signature = TransactionEncoder.signMessage(etherTransaction,chainId.getChainId().longValue(),credentials);
        String signatureHexValue = Numeric.toHexString(signature);
        EthSendTransaction ethSendTransaction = client.ethSendRawTransaction(signatureHexValue).sendAsync().get();
        EthGetBalance ethGetBalance = client.ethGetBalance(Environment.From_Eth_address,
                DefaultBlockParameterName.fromString(DefaultBlockParameterName.LATEST.name())
        ).sendAsync().get();
        BigInteger balance = ethGetBalance.getBalance();
        System.out.println("account balance :" + ethGetBalance.getBalance());
        System.out.println("ethSendTransaction tx:" + ethSendTransaction.getResult());
    }
}
