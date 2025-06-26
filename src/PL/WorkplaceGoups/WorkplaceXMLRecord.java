package PL.WorkplaceGoups;

public class WorkplaceXMLRecord {
    private String name;
    private  int id;
    private String groupName;
    private boolean sumRelevant;

    public WorkplaceXMLRecord( int id, String name,String groupName, boolean sumRelevant) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
        this.sumRelevant = sumRelevant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isSumRelevant() {
        return sumRelevant;
    }
    public void setSumRelevant(boolean sumRelevant) {
        this.sumRelevant = sumRelevant;
    }
}
