package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToAddCurPtCruveToPTCurves extends XLoggableCmd {
    //fields
    private int mNumOfPtCurvesBef = Integer.MIN_VALUE;
    private int mNumOfPtCurvesAft = Integer.MIN_VALUE;
    
    //constructor
    private JSICmdToAddCurPtCruveToPTCurves(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JSICmdToAddCurPtCruveToPTCurves cmd = 
            new JSICmdToAddCurPtCruveToPTCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        if (app.getPtCurveMgr().getCurPtCurve() != null) {
            if (app.getPtCurveMgr().getCurPtCurve().getPts().size() == 1) {
                app.getPtCurveMgr().getPtCurves().addAll(app.getPtCurveMgr().
                    getSelectedPtCurves());
                app.getPtCurveMgr().getSelectedPtCurves().clear();
                return false;
            } else {
                this.mNumOfPtCurvesBef = 
                    app.getPtCurveMgr().getPtCurves().size();
                app.getPtCurveMgr().getPtCurves().add(
                    app.getPtCurveMgr().getCurPtCurve());
                this.mNumOfPtCurvesAft = 
                    app.getPtCurveMgr().getPtCurves().size();
                return true;
            }
        }
        return false;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mNumOfPtCurvesBef).append("\t");
        sb.append(this.mNumOfPtCurvesAft).append("\t");
        return sb.toString();
    }
}
