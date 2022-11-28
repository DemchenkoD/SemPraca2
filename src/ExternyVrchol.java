import java.util.BitSet;

public class ExternyVrchol implements IVrchol{
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

    public String getKey() {
        //BitSet key;
        String result = null;
        if (parent != null) {
            char[] bits = new char [parent.getIndexSplitter() + 1];
            //key = new BitSet(parent.getIndexSplitter() + 1);
            InternyVrchol tmp_parent = parent;
            IVrchol tmp_son = this;
            for (int i = 0 ; i < parent.getIndexSplitter() + 1; i++) {
                if(tmp_parent.isItRightSon(tmp_son))
                    bits[parent.getIndexSplitter() -i] = '1';
                else
                    bits[parent.getIndexSplitter() -i] = '0';
                //key.set(parent.getIndexSplitter()-i, tmp_parent.isItRightSon(tmp_son));
                tmp_parent = tmp_parent.getParent();
                tmp_son = tmp_son.getParent();
            }
            result = String.valueOf(bits);

        } else {
            result = "null";
        }
        return result;//key;
    }
    public void print() {
        /*BitSet key = getKey();
        StringBuilder s = new StringBuilder();
        for( int i = 0; i < key.length();  i++ )
        {
            s.append( key.get( i ) == true ? 1: 0 );
        }*/
        System.out.println("Key " + getKey() + "\tAdresa " + adresaBloku);
    }
    public String toString() {
        return getKey() + ";" + adresaBloku +'\n';
    }
    public void setAdresaBloku(long adresaBloku) {
        this.adresaBloku = adresaBloku;
    }

    @Override
    public boolean isInternal() {
        return false;
    }


}
