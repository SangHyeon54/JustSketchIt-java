package jsi;

import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JFrame;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class JSIApp extends XApp {
    // Declare some member variables (field)
    // mFrame= member Frame
    private JFrame mFrame = null; 
    private JSICanvas2D mCanvas2D = null;
    public JSICanvas2D getCanvas2D() {
        return mCanvas2D;
    }

    private JSIXform mXform = null;
    public JSIXform getXform() {
        return this.mXform;
    }

    private JSIColorChooser mColorChooser = null;
    public JSIColorChooser getColorChooser() {
        return mColorChooser;
    }
    
    private JSIEventListener mEventListener = null;
    
    private JSIPenMarkMgr mPenMarkMgr = null;
    public JSIPenMarkMgr getPenMarkMgr() {
        return this.mPenMarkMgr;
    }
    
    private JSIPtCurveMgr mPtCurveMgr = null;
    public JSIPtCurveMgr getPtCurveMgr() {
        return this.mPtCurveMgr;
    }

    private XScenarioMgr mScenarioMgr = null;
    @Override
    public XScenarioMgr getScenarioMgr() {
        return this.mScenarioMgr;
    }

    private XLogMgr mLogMgr = null;
    @Override
    public XLogMgr getLogMgr() {
        return this.mLogMgr;
    }
    
    // Constructor
    public JSIApp() {
        // step 1: create components
        // 1) frame, 2) canvas, 3) other components,
        // 4) eventlistener 5) manager
        this.mFrame = new JFrame("JustSketchIt");
        this.mCanvas2D = new JSICanvas2D(this);
        this.mXform = new JSIXform();
        this.mColorChooser = new JSIColorChooser();
        this.mEventListener = new JSIEventListener(this);
        this.mPenMarkMgr = new JSIPenMarkMgr();
        this.mPtCurveMgr = new JSIPtCurveMgr();
        this.mScenarioMgr = new JSIScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        mLogMgr.setPrintOn(true);

        // step 2: build and show visual components
        this.mFrame.add(this.mCanvas2D);
        this.mFrame.setSize(800,600);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
        
        // step 3: connect event listeners.
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.addKeyListener(this.mEventListener);
        this.mCanvas2D.setFocusable(true);
    }

    public static void main(String[] args) {
        // Create a new JSI object
        new JSIApp();
    }
    
    public void increaseStrokeWidthForSelected(float f) {
        //from the point curve manager, get number of the selected point curves
        int size_s = this.mPtCurveMgr.getSelectedPtCurves().size();
        for (int i = 0; i < size_s; i++) {
            BasicStroke bs = (BasicStroke)this.mPtCurveMgr.
                getSelectedPtCurves().get(i).getStroke();
            float w = bs.getLineWidth();
            w += f;
            if (w < 1.0f) {
                w = 1.0f;
            }
            
            //reset the stroke with new width
            this.mPtCurveMgr.getSelectedPtCurves().get(i).
                setStroke(new BasicStroke(w, bs.getEndCap(), bs.getLineJoin()));
        }
    }
    
    public void setColorForSelectedPtCurve(Color c) {
        int size_s = this.mPtCurveMgr.getSelectedPtCurves().size();
        for (int i = 0; i < size_s; i++) {
            this.mPtCurveMgr.getSelectedPtCurves().get(i).setColor(c);
        }
    }
}
