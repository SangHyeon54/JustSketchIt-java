package jsi.cmd;

import java.awt.Color;
import java.awt.Point;
import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToChangeSelectedPtCurveColor extends XLoggableCmd{
    private Point mScreenPt = null;
    private Color mColor = null;
    
    //private constructor
    private JSICmdToChangeSelectedPtCurveColor(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        JSICmdToChangeSelectedPtCurveColor cmd = 
            new JSICmdToChangeSelectedPtCurveColor(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        //get the color
        Color c = app.getColorChooser().calcColor(this.mScreenPt, 
            app.getCanvas2D().getWidth(), 
            app.getCanvas2D().getHeight());
        this.mColor = c;
      
        //set the color
        app.setColorForSelectedPtCurve(c);
        app.getPtCurveMgr().getPtCurves().addAll(
            app.getPtCurveMgr().getSelectedPtCurves());
        app.getPtCurveMgr().getSelectedPtCurves().clear();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mColor).append("\t");
        return sb.toString();
    } 
}
