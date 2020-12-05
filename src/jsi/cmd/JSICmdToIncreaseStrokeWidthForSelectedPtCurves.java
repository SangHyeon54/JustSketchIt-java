package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToIncreaseStrokeWidthForSelectedPtCurves extends 
    XLoggableCmd {
    // fields
    private float mWidthDelta = Float.NaN;
    
    // private constructor
    private JSICmdToIncreaseStrokeWidthForSelectedPtCurves(
        XApp app, float dw) {
        super(app);
        this.mWidthDelta = dw;
    }
    
    public static boolean execute(XApp app, float dw) {
        JSICmdToIncreaseStrokeWidthForSelectedPtCurves cmd = 
            new JSICmdToIncreaseStrokeWidthForSelectedPtCurves(app, dw);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        app.increaseStrokeWidthForSelected(this.mWidthDelta);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mWidthDelta).append("\t");
        return sb.toString();
    }
}
