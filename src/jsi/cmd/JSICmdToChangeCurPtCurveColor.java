package jsi.cmd;

import java.awt.Color;
import java.awt.Point;
import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToChangeCurPtCurveColor extends XLoggableCmd {
    private Point mScreenPt = null;
    private Color mColor = null;
    
    //private constructor
    private JSICmdToChangeCurPtCurveColor(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        JSICmdToChangeCurPtCurveColor cmd = 
            new JSICmdToChangeCurPtCurveColor(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        Color c = app.getColorChooser().calcColor(this.mScreenPt, 
            app.getCanvas2D().getWidth(), 
            app.getCanvas2D().getHeight());
        this.mColor = c;
        app.getCanvas2D().setCurColorForPtCurve(c);
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
