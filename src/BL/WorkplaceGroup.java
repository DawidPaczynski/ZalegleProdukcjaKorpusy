package BL;

import java.util.List;

public class WorkplaceGroup {
    private final String name;
    private List<Workplace> workplaces;

    public WorkplaceGroup(String name, List<Workplace> workplaces) {
        this.name = name;
        this.workplaces = workplaces;
    }

    public String getName() {
        return name;
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }
}
