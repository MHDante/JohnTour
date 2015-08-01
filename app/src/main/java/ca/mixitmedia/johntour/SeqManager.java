package ca.mixitmedia.johntour;

/**
 * Created by MHDante on 2015-07-31.
 */
public class SeqManager {
    private static SeqManager ourInstance = new SeqManager();

    public static SeqManager getInstance() {
        return ourInstance;
    }

    public static SequencePoint getCurrentSeqPt(){
        return SequencePoint.list.get(SeqManager.getInstance().currentPt());
    }
    private SeqManager() {
    }

    public int currentPt() {
        return 0;
    }
}
