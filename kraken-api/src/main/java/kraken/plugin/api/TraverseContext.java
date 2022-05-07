package kraken.plugin.api;

public class TraverseContext {

    private double skipThreshold = 30000.0;
    private Vector3i destination;
    private Vector3i lastTile;
    private Vector3i lastWalk;
    private boolean finished;

    public double getSkipThreshold() {
        return skipThreshold;
    }

    public void setSkipThreshold(double skipThreshold) {
        this.skipThreshold = skipThreshold;
    }

    public Vector3i getLastTile() {
        return lastTile;
    }

    public void setLastTile(Vector3i lastTile) {
        this.lastTile = lastTile;
    }

    public Vector3i getLastWalk() {
        return lastWalk;
    }

    public void setLastWalk(Vector3i lastWalk) {
        this.lastWalk = lastWalk;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Vector3i getDestination() {
        return destination;
    }

    public void setDestination(Vector3i destination) {
        this.destination = destination;
    }
}
