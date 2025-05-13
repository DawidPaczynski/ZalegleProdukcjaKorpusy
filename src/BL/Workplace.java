package BL;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Workplace {
    private final int id;
    private String name;
    private TreeMap<String, List<QueryRecord>> productionOrders;
    private TreeMap<String,Float> productionTimes;

    public Workplace(int id) {
        this.id = id;
        this.productionOrders = new TreeMap<>();
        this.productionTimes = new TreeMap<>();
    }

    public void addProductionOrder(QueryRecord record) {
        if(!productionOrders.containsKey(record.getBlock())){
            productionOrders.put(record.getBlock(), new ArrayList<>());
        }
        productionOrders.get(record.getBlock()).add(record);
        if(!productionTimes.containsKey(record.getBlock())){
            productionTimes.put(record.getBlock(), 0f);
        }
        Float currentTimeSum = productionTimes.get(record.getBlock());
        currentTimeSum += record.getProductionTime();
        productionTimes.put(record.getBlock(), currentTimeSum);;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeMap<String, List<QueryRecord>> getProductionOrders() {
        return productionOrders;
    }

    public TreeMap<String, Float> getProductionTimes() {
        return productionTimes;
    }
}
