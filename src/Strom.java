import java.util.ArrayList;
import java.util.BitSet;

public class Strom {
    private IVrchol Root;
    public Strom() {
        Root = new ExternyVrchol(null, 0);
    }
    public ExternyVrchol getExternyVrchol(BitSet key) {
        //if (!Root.isInternal()){
        //    return Root;
        //}
        IVrchol vrchol = Root;
        while (vrchol instanceof InternyVrchol) {
            vrchol = ((InternyVrchol) vrchol).getSon(key);
        }
        return (ExternyVrchol) vrchol;

    }

    public boolean transformAndInsert(ExternyVrchol extVrchol){
        return true;
    }



}
