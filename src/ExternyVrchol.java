import java.util.BitSet;

public class ExternyVrchol implements Comparable<ExternyVrchol>, IVrchol{
    private InternyVrchol parent;
    private long adresaBloku;
    public boolean visited;
    public ExternyVrchol(InternyVrchol paParent, long paAdresaBloku) {
        parent = paParent;
        this.adresaBloku = paAdresaBloku;

    }

    public InternyVrchol getParent() {
        return parent;
    }

    public long getAdresaBloku() {
        return adresaBloku;
    }

    public BitSet getKey() {
        BitSet key;
        if (parent != null) {
            key = new BitSet(parent.getIndexSplitter() + 1);
            InternyVrchol tmp_parent = parent;
            IVrchol tmp_son = this;
            for (int i = 0 ; i < parent.getIndexSplitter() + 1; i++) {
                key.set(key.size()-i, tmp_parent.isItRightSon(tmp_son));
                tmp_parent = tmp_parent.getParent();
                tmp_son = tmp_son.getParent();
            }

        } else {
            key = null;
        }
        return key;
    }
    public void setAdresaBloku(long adresaBloku) {
        this.adresaBloku = adresaBloku;
    }

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public int compareTo(ExternyVrchol o) {
        return 0;
    }
}
