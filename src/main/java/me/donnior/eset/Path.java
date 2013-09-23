package me.donnior.eset;

public class Path {
    private Path up;
    private Path down;
    private String name;
    private String[] names;

    public Path(String key) {
        this.name = key.replace("]", "");
        System.out.println(this.name);
        this.names = this.name.split("\\[");
    }

    public Path down() {
        return this.down;
    }

    public boolean hasDown() {
        return this.down != null;
    }

    public String name() {
        return this.name;
    }

    public boolean isArray() {
        // TODO Auto-generated method stub
        return false;
    }

    public Path up() {
        // TODO Auto-generated method stub
        return null;
    }
}
