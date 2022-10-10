abstract class Player {
    private int id;

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    abstract public int chooseColumn();
}
