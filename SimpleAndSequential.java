
public class SimpleAndSequential {
    private final int x;
    private final int y;
    private float gridX;
    private float gridY;
    private int totalPop;
    private final CensusData censusData;
    /*
     * Before processing any queries, process the data to find the four corners
     * of the U.S. rectangle using a sequential O(n) algorithm where n is the
     * number of census-block-groups.  Then for each query do another
     * sequential O(n) traversal to answer the query (determining for each
     * census-block-group whether or not it is in the query rectangle).
     * The simplest and most reusable approach for each census-block-group is
     * probably to first compute what grid position it is in and then see if
     * this grid position is in the query rectangle.
     */


    public int query(int west, int south, int east, int north) {
        CensusGroup group;
        int population = 0;
        float gLong, gLat;
        for (int i = 0; i < censusData.data_size; i++) {
            group = censusData.data[i];
            gLong = group.longitude;
            gLat = group.latitude;
            if (gLong >= (south * gridY)  &&
                    gLong < (north * gridY) &&
                    gLat > (east * gLat) &&
                    gLat <= (west * gLat))
                population += group.population;
        }
        return population;
    }

    // maybe modify to use rectangle
    public int preprocess(CensusData data) {
        CensusGroup group = data.data[0];
        float west, east, north, south;
        west = east = group.latitude;
        north = south = group.longitude;
        for (int i = 1; i < data.data_size; i++) {
            group = data.data[i];
            if (group.longitude < south)
                south = group.longitude;
            if (group.longitude > north)
                north = group.longitude;
            if (group.latitude < east)
                east = group.latitude;
            if (group.latitude > west)
                west = group.latitude;
            totalPop += group.population;
        }
        gridX = (west - east) / x;
        gridY = (north - south) / y;
        return totalPop;
    }

    public boolean queryChecker(int west, int south, int east, int north) {
        return !(west < 1 || west > x ||
                south < 1 || south > y ||
                east < west || east > x ||
                north < south || north > y);
    }

    public SimpleAndSequential(int x, int y, CensusData data) {
        this.x = x;
        this.y = y;
        gridX = 0;
        gridY = 0;
        this.censusData = data;
        totalPop = 0;
    }
}
