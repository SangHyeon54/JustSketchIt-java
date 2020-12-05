package jsi.cmd;

import java.awt.Point;
import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToPan extends XLoggableCmd {
    private Point mScreenPt = null;
    
    //private constructor
    private JSICmdToPan(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        JSICmdToPan cmd = new JSICmdToPan(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        app.getXform().translateTo(this.mScreenPt);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mScreenPt).append("\t");
        return sb.toString();
    }
}
