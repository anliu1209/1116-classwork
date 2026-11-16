public class Location {
    private int row;
    private int column;

    public Location(int row, int column) {
        this.row = row;                 
        this.column = column;
    }

    public String toString() {
        return "("+row+", "+column+")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Location other = (Location) obj;
        return (this.row == other.row) && (this.column == other.column);
    }
    
    @Override
    public int hashCode() {
        if (row == null || column == null || row < 0 || column < 0) return 0;
        return row * 26 + column;
    }
}