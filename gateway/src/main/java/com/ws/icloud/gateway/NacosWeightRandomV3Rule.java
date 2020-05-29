/*
package com.ws.icloud.gateway;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ZonePreferenceServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
public class NacosWeightRandomV3Rule extends BlockingLoadBalancerClient {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    public NacosWeightRandomV3Rule(LoadBalancerClientFactory loadBalancerClientFactory) {
        super(loadBalancerClientFactory);
    }


    @Override
    public ServiceInstance choose(String serviceId) {
        try {
            Instance instance = discoveryProperties.namingServiceInstance()
                    .selectOneHealthyInstance(serviceId);
            NacosServiceInstance nacosServiceInstance = new NacosServiceInstance();
            nacosServiceInstance.setServiceId(instance.getInstanceId());
            nacosServiceInstance.setHost(instance.getIp());
            nacosServiceInstance.setPort(instance.getPort());
            return nacosServiceInstance;
        } catch (NacosException e) {
         log.info(e.getMessage());
        }
        return null;
    }


}
*/
