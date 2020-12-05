package jsi.scenario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import jsi.JSIApp;
import jsi.JSIColorChooser;
import jsi.JSIScene;
import jsi.cmd.JSICmdToChangeCurPtCurveColor;
import jsi.cmd.JSICmdToChangeSelectedPtCurveColor;
import jsi.cmd.JSICmdToRecalcOpaquenessOfColorChooser;
import jsi.cmd.JSICmdToRecalcSaturationOfColorChooser;
import jsi.cmd.JSICmdToSetAnchorOpaqueness;
import jsi.cmd.JSICmdToSetAnchorSaturation;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class JSIColorScenario extends XScenario {
    private static JSIColorScenario mSingleton = null;
    public static JSIColorScenario getSingle() {
        assert(JSIColorScenario.mSingleton != null);
        return JSIColorScenario.mSingleton;
    }
    
    public static JSIColorScenario createSingleton(XApp app) {
        assert(JSIColorScenario.mSingleton == null);
        JSIColorScenario.mSingleton = new JSIColorScenario(app);
        return JSIColorScenario.mSingleton;
    }
    
    private JSIColorScenario(XApp app) {
        super(app);
        JSIApp jsiapp  = (JSIApp) app;
        this.mColorChooser = jsiapp.getColorChooser();
    }
    
    @Override
    protected void addScenes() {
        this.addScene(ColorReadyScene.createSingleton(this));
        this.addScene(ChangeColorScene.createSingleton(this));
        this.addScene(ChangeSaturationScene.createSingleton(this));
        this.addScene(ChangeOpaquenessScene.createSingleton(this));
    }
    
    public static class ColorReadyScene extends JSIScene {
        private static ColorReadyScene mSingleton = null;
        public static ColorReadyScene getSingleton() {
            assert(ColorReadyScene.mSingleton != null);
            return ColorReadyScene.mSingleton;
        }
        
        public static ColorReadyScene createSingleton(XScenario scenario) {
            assert(ColorReadyScene.mSingleton == null);
            ColorReadyScene.mSingleton = new ColorReadyScene(scenario);
            return ColorReadyScene.mSingleton;
        }
        
        private ColorReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            
            //calculate suitable area
            Point pt = e.getPoint();
            JSIColorChooser.Area area = app.getColorChooser().decideArea(
                pt, app.getCanvas2D().getWidth(), 
                app.getCanvas2D().getHeight());
            
            //depending on the area, it calls corresponding command
            switch (area) {
                case HUE:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ChangeColorScene.getSingleton(),
                        this.getReturnScene());
                    break;
                case SATURATION:
                    JSICmdToSetAnchorSaturation.execute(app);
                    XCmdToChangeScene.execute(app, 
                        ChangeSaturationScene.getSingleton(),
                        this.mReturnScene);
                    break;
                case OPAQUENESS:
                    JSICmdToSetAnchorOpaqueness.execute(app);
                    XCmdToChangeScene.execute(app, 
                        ChangeOpaquenessScene.getSingleton(),
                        this.mReturnScene);
                    break;
            }        
            
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        this.getReturnScene(), 
                        null);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {
        }

        @Override
        public void renderWorldOjbects(Graphics2D g2) {
        }

        @Override
        public void renderScreenOjbects(Graphics2D g2) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
        
    }
    
    public static class ChangeColorScene extends JSIScene {
        private static ChangeColorScene mSingleton = null;
        public static ChangeColorScene getSingleton() {
            assert(ChangeColorScene.mSingleton != null);
            return ChangeColorScene.mSingleton;
        }
        
        public static ChangeColorScene createSingleton(XScenario scenario) {
            assert(ChangeColorScene.mSingleton == null);
            ChangeColorScene.mSingleton = new ChangeColorScene(scenario);
            return ChangeColorScene.mSingleton;
        }
        
        private ChangeColorScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            Point pt = e.getPoint();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Color c = app.getColorChooser().calcColor(pt, 
                app.getCanvas2D().getWidth(), app.getCanvas2D().getHeight());
            
            if (app.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                JSICmdToChangeCurPtCurveColor.execute(app, pt);
            } else {
                JSICmdToChangeSelectedPtCurveColor.execute(app, pt);
            }
            
            XCmdToChangeScene.execute(app, 
                JSIDefaultScenario.ReadyScene.getSingleton(), null);
            
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        this.getReturnScene(), 
                        null);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {
        }

        @Override
        public void renderWorldOjbects(Graphics2D g2) {
        }

        @Override
        public void renderScreenOjbects(Graphics2D g2) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
        
    }
    
    public static class ChangeSaturationScene extends JSIScene {

        private static ChangeSaturationScene mSingleton = null;
        public static ChangeSaturationScene getSingleton() {
            assert(ChangeSaturationScene.mSingleton != null);
            return ChangeSaturationScene.mSingleton;
        }
        
        public static ChangeSaturationScene createSingleton(
            XScenario scenario) {
            assert(ChangeSaturationScene.mSingleton == null);
            ChangeSaturationScene.mSingleton = 
                new ChangeSaturationScene(scenario);
            return ChangeSaturationScene.mSingleton;
        }
        
        private ChangeSaturationScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSICmdToRecalcSaturationOfColorChooser.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(app, 
                JSIColorScenario.ColorReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ColorReadyScene.getSingleton(),
                        this.getReturnScene());
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {
        }

        @Override
        public void renderWorldOjbects(Graphics2D g2) {
        }

        @Override
        public void renderScreenOjbects(Graphics2D g2) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
        
    }
    
    //almost the same as above saturation scene
    public static class ChangeOpaquenessScene extends JSIScene {
        private static ChangeOpaquenessScene mSingleton = null;
        public static ChangeOpaquenessScene getSingleton() {
            assert(ChangeOpaquenessScene.mSingleton != null);
            return ChangeOpaquenessScene.mSingleton;
        }
        
        public static ChangeOpaquenessScene createSingleton(
            XScenario scenario) {
            assert(ChangeOpaquenessScene.mSingleton == null);
            ChangeOpaquenessScene.mSingleton = 
                new ChangeOpaquenessScene(scenario);
            return ChangeOpaquenessScene.mSingleton;
        }
        
        private ChangeOpaquenessScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSICmdToRecalcOpaquenessOfColorChooser.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(app, 
                JSIColorScenario.ColorReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ColorReadyScene.getSingleton(),
                        this.getReturnScene());
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {
        }

        @Override
        public void renderWorldOjbects(Graphics2D g2) {
        }

        @Override
        public void renderScreenOjbects(Graphics2D g2) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
    }
    
    private JSIColorChooser mColorChooser = null;
    public JSIColorChooser getColorChooser() {
        return mColorChooser;
    }
    
    private void drawColorChooser(Graphics2D g2) {
        JSIApp app = (JSIApp) this.getApp();
        JSIScene curScene = (JSIScene) app.getScenarioMgr().getCurScene();
        app.getColorChooser().drawCells(g2, app.getCanvas2D().getWidth(),
            app.getCanvas2D().getHeight());
    }
}
