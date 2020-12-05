package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToDeselectSelectedPtCurves extends XLoggableCmd {
    //fields
    int selectedNum = Integer.MIN_VALUE;
    
    private JSICmdToDeselectSelectedPtCurves(XApp app) {
        super(app);
        JSIApp jsiApp = (JSIApp) app;
        this.selectedNum = jsiApp.getPtCurveMgr().getSelectedPtCurves().size();
    }
    
    public static boolean execute(XApp app) {
        JSICmdToDeselectSelectedPtCurves cmd = 
            new JSICmdToDeselectSelectedPtCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        app.getPtCurveMgr().getPtCurves().addAll(
            app.getPtCurveMgr().getSelectedPtCurves());
        app.getPtCurveMgr().getSelectedPtCurves().clear();
        return true;
    }

    @Override
    protected String createLog() {
        JSIApp app = (JSIApp) this.mApp;
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.selectedNum).append("\t");
        sb.append(app.getPtCurveMgr().getSelectedPtCurves().size());
        return sb.toString();
    }
    
}
