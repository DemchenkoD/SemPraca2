import java.util.BitSet;

public class InternyVrchol implements Comparable<InternyVrchol>, IVrchol{

    private InternyVrchol lavy = null;
    private InternyVrchol pravy = null;
    private InternyVrchol parent;
    private BitSet key;
    int indexSplitter;
    public InternyVrchol(InternyVrchol paParent, BitSet paKey, int paIndexSplitter) {
        this.indexSplitter = paIndexSplitter;
        this.key = paKey;
        this.parent = paParent;
    }

    public void setLavy(InternyVrchol lavy) {
        this.lavy = lavy;
    }

    public void setPravy(InternyVrchol pravy) {
        this.pravy = pravy;
    }
    public InternyVrchol getLavy() {
        return lavy;
    }

    public InternyVrchol getPravy() {
        return pravy;
    }

    @Override
    public int compareTo(InternyVrchol o) {
        int myInt = this.key.get(indexSplitter) ? 1 : 0;
        int oInt = o.key.get(indexSplitter)? 1 : 0;
        if ( myInt > oInt)
            return 1;
        else if (myInt < oInt)
            return -1;
        else
            return 0;

    }

    @Override
    public boolean isInternal() {
        return true;
    }
}
