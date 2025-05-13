package PL.WorkplaceGoups;

public class WorkplaceXMLRecord {
    private String name;
    private  int id;
    private String groupName;

    public WorkplaceXMLRecord( int id, String name,String groupName) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
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
}
