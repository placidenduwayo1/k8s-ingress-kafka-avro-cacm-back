package fr.placide.cacmbsmsaddress.domain.topics;

import java.util.List;
public class TopicsParams {
    private TopicsParams(){}
    public static final List<String> TOPICS= List.of("address-created","address-edited","address-deleted");
    public static final int NB_PARTITIONS = 1;
    public static final int NB_REPLICAS =1;
}
