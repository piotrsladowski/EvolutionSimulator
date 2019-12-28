package evolutionSimulator.Models.Species;

public class Plant {
    private int ID;
    private String name;

    public Plant(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
