package BL;

import java.util.List;

public class WorkplaceGroup {
    private final String name;
    private List<Workplace> workplaces;
    private final int id;

    public WorkplaceGroup(String name, List<Workplace> workplaces) {
        this.name = name;
        this.workplaces = workplaces;
        try{
            this.id = Integer.parseInt(name.split("\\.")[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Nazwa grupy musi być poprzedzona liczbą i kropką, np: 2.");
        }
    }

    public String getName() {
        return name;
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public int getId() {
        return id;
    }
}
