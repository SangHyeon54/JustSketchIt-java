package jsi;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class JSIPenMarkMgr {
    
    // constants
    public static final int MAX_NUM_PEN_MARKS = 10;
    
    // fields
    private ArrayList<JSIPenMark> mPenMarks = null;
    public ArrayList<JSIPenMark> getPenMarks() {
        return this.mPenMarks;
    }
    
    // constructor
    public JSIPenMarkMgr () {
        this.mPenMarks = new ArrayList<JSIPenMark>();
    }
    
    public void addPenMark(JSIPenMark penMark) {
        this.mPenMarks.add(penMark);
        if (this.mPenMarks.size() > JSIPenMarkMgr.MAX_NUM_PEN_MARKS) {
            this.mPenMarks.remove(0);
            assert(this.mPenMarks.size() <= JSIPenMarkMgr.MAX_NUM_PEN_MARKS);
        }
    }
    
    public JSIPenMark getLastPenMark() {
        int size = this.mPenMarks.size();
        if (size == 0) {
            return null;
        }
        else {
            return this.mPenMarks.get(size - 1);
        } 
    }
    
    // the last pen mark if i = 0;
    public JSIPenMark getRecentPenMark(int i) {
        int size = this.mPenMarks.size();
        int index = size - 1 - i;
        if (index < 0 || index >= size) {
            return null;
        } else {
            return this.mPenMarks.get(index);
        }
    }
    
    public boolean mousePressed(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPenMark penMark = new JSIPenMark(pt);
        this.addPenMark(penMark);
        return true;
    }
    
    public boolean mouseDragged(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPenMark penMark = this.getLastPenMark();
        //if penmark is not null then return true
        return penMark != null && penMark.addPt(pt);
    }
    
    public boolean mouseReleased(MouseEvent e) {
        return true;
    }
}
