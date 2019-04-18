package com.corsair.sparrow.pirate.gamma.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jack
 */
@Slf4j
@Setter
@Getter
public class ThriftServerDiscovery implements InitializingBean {

    private Random random = new Random();
    private String zkServerList;
    private ZkClient zkClient;
    private static Map<String, List<String>> map = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        zkClient = new ZkClient(zkServerList);
        // 启动时发现服务
        discover();
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                // 断开重连之后从新发现
                if (state.equals(Watcher.Event.KeeperState.SyncConnected)) {
                    discover();
                }
            }

            @Override
            public void handleNewSession() throws Exception {
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {
            }
        });
    }

    private void discover() {
        List<String> serviceList = zkClient.getChildren("/rpc");
        if (log.isInfoEnabled()) {
            log.info(String.format("Existing service provider : %s", serviceList));
        }
        ChildListener childListener = new ChildListener();
        for (String service : serviceList) {
            List<String> children = zkClient.getChildren("/rpc/"+service);
            List<String> addressList = new ArrayList<>();
            addressList = parse(children);
            map.put(service, addressList);
            zkClient.subscribeChildChanges("/rpc/" + service, childListener);
        }

        zkClient.subscribeChildChanges("/rpc", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> children) throws Exception {
                for (String child : children) {
                    List<String> old = map.putIfAbsent(child, new ArrayList<>());
                    if (old == null) {
                        zkClient.subscribeChildChanges("/rpc/" + child, childListener);
                        if (log.isInfoEnabled()) {
                            log.info(String.format("New provider %s for service", children, parentPath));
                        }
                    }
                }
            }
        });
    }

    private static final class ChildListener implements IZkChildListener {
        @Override
        public void handleChildChange(String parentPath, List<String> children) throws Exception {
            String key = parentPath.replace("/rpc/", "");
            List<String> addressList = new ArrayList<>();
            if (children == null || children.size() == 0) {
                map.put(key, addressList);
                return;
            }
            addressList = parse(children);
            map.put(key, addressList);
        }
    }

    private static List<String> parse(List<String> nodes) {
        List<String> addressList = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) {
            return addressList;
        }
        for (String node : nodes) {
            String[] str = node.split(":");
            if (str.length == 3) {
                int weight = Integer.valueOf(str[2]);
                for (int i = 0; i < weight; i++) {
                    addressList.add(str[0] + ":" + str[1]);
                }
            }
        }
        return addressList;
    }

    public String getAddress(String serviceName, String version) {
        String key = serviceName + "_" + version;
        List<String> addressList = map.get(key);
        if (addressList == null || addressList.size() == 0) {
            return null;
        }
        int index = random.nextInt(addressList.size());
        return addressList.get(index);
    }


}

