package com.ws.icloud.gateway;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NacosWeightRandomV2Rule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public Server choose(Object key) {
        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
        String name = loadBalancer.getName();
        Instance instance = null;
        try {
            instance = discoveryProperties.namingServiceInstance()
                    .selectOneHealthyInstance(name);
        //测试不需要nacos服务端是否可以调用
            //  Instance instance= JSON.parseObject("{\"clusterName\":\"DEFAULT\",\"enabled\":true,\"ephemeral\":true,\"healthy\":true,\"instanceHeartBeatInterval\":5000,\"instanceHeartBeatTimeOut\":15000,\"instanceId\":\"10.198.254.1#9001#DEFAULT#DEFAULT_GROUP@@user\",\"ip\":\"10.198.254.1\",\"ipDeleteTimeout\":30000,\"metadata\":{\"preserved.register.source\":\"SPRING_CLOUD\"},\"port\":9001,\"serviceName\":\"DEFAULT_GROUP@@user\",\"weight\":1.0}",Instance.class);


        } catch (NacosException e) {
            e.printStackTrace();
        }


        log.info("选中的instance = {}", instance);

        /*
         * instance转server的逻辑参考自：
         * org.springframework.cloud.alibaba.nacos.ribbon.NacosServerList.instancesToServerList
         */
        return new NacosServer(instance);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }
}
