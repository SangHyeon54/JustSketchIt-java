package jsi.cmd;

import java.awt.Point;
import jsi.scenario.JSISelectScenario;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToUpdateSelectionBox extends XLoggableCmd {
        //fields
    private Point mScreenPt = null;

    //private constructor
    private JSICmdToUpdateSelectionBox(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        JSICmdToUpdateSelectionBox cmd = new JSICmdToUpdateSelectionBox(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSISelectScenario scenario = JSISelectScenario.getSingleton();
        scenario.getSelectionBox().update(mScreenPt);
        scenario.updateSelectedPtCurves();
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
