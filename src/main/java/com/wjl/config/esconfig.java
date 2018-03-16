package com.wjl.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * Created by wujianlong on 2017/6/30.
 */
@Configuration
public class esconfig {

    @Value("${estemplate.host}")
    private String host;

    @Value("${estemplate.port}")
    private String port;

    @Value("${estemplate.cluster-name}")
    private String clusterName;

    @Bean("esClient")
    public TransportClient creatClient() throws UnknownHostException, InterruptedException, ExecutionException {
        Settings esSettings = Settings.builder()
                .put("cluster.name", clusterName) //设置ES实例的名称
                .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .build();
        TransportClient client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
        //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
        return client;

    }


    @Bean("elasticsearchTemplate")
    public ElasticsearchTemplate elasticsearchTemplate() throws InterruptedException, ExecutionException, UnknownHostException {
        return new ElasticsearchTemplate(creatClient());
    }

}
