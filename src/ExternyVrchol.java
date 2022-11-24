public class ExternyVrchol implements Comparable<ExternyVrchol>, IVrchol{
    private InternyVrchol parent;
    private long adresaBloku;
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
