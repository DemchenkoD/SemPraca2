import java.util.BitSet;

public class InternyVrchol implements Comparable<InternyVrchol>, IVrchol{

    private IVrchol lavy = null;
    private IVrchol pravy = null;
    private InternyVrchol parent;
    public boolean visited;
    //private BitSet key;
    int indexSplitter;
    public InternyVrchol(InternyVrchol paParent, int paIndexSplitter) {
        this.indexSplitter = paIndexSplitter;
        //this.key = paKey;
        this.parent = paParent;
    }

    public InternyVrchol getParent() {
        return parent;
    }

    public int getIndexSplitter() {
        return indexSplitter;
    }

    public void setLavy(IVrchol lavy) {
        this.lavy = lavy;
    }

    public void setPravy(IVrchol pravy) {
        this.pravy = pravy;
    }
    public IVrchol getLavy() {
        return lavy;
    }

    public IVrchol getPravy() {
        return pravy;
    }

    public IVrchol getSon(BitSet b) {
        int val = b.get(indexSplitter)? 1 : 0;
        if ( val == 1)
            return pravy;
        else if ( val == 0 )
            return lavy;
        else
            return null;
    }
    public void setSon(IVrchol son, IVrchol replacement) {
        if (pravy == son)
            pravy = replacement;
        else if (lavy == son)
            lavy = replacement;
    }
    public IVrchol getBrother(IVrchol paBrother) {
        if (paBrother == lavy)
            return pravy;
        else if (paBrother == pravy)
            return lavy;
        else
            return null;
    }
    public boolean isItRightSon(IVrchol son) {
        if (pravy == son)
            return true;

        return false;
    }

    @Override
    public int compareTo(InternyVrchol o) { //TODO Remove
        /*int myInt = this.key.get(indexSplitter) ? 1 : 0;
        int oInt = o.key.get(indexSplitter)? 1 : 0;
        if ( myInt > oInt)
            return 1;
        else if (myInt < oInt)
            return -1;
        else
        */    return 0;

    }

    @Override
    public boolean isInternal() {
        return true;
    }
}
