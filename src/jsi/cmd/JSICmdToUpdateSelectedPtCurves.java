/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsi.cmd;

import jsi.JSIApp;
import jsi.scenario.JSISelectScenario;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToUpdateSelectedPtCurves extends XLoggableCmd {
    //private constructor
    private JSICmdToUpdateSelectedPtCurves(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JSICmdToUpdateSelectedPtCurves cmd = 
            new JSICmdToUpdateSelectedPtCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSISelectScenario scenario = JSISelectScenario.getSingleton();
        scenario.updateSelectedPtCurves();
        return true;
    }

    @Override
    protected String createLog() {
        JSIApp app = (JSIApp) this.mApp;
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(app.getPtCurveMgr().getSelectedPtCurves().size());
        return sb.toString();
    }
}
