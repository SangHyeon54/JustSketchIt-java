package jsi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.cmd.JSICmdToCreateCurPtCurve;
import jsi.cmd.JSICmdToIncreaseStrokeWidthForCurPtCurve;
import jsi.cmd.JSICmdToHome;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class JSIDefaultScenario extends XScenario {
    private static JSIDefaultScenario mSingleton = null;
    public static JSIDefaultScenario getSingle() {
        assert(JSIDefaultScenario.mSingleton != null);
        return JSIDefaultScenario.mSingleton;
    }
    
    public static JSIDefaultScenario createSingleton(XApp app) {
        assert(JSIDefaultScenario.mSingleton == null);
        JSIDefaultScenario.mSingleton = new JSIDefaultScenario(app);
        return JSIDefaultScenario.mSingleton;
    }
    
    private JSIDefaultScenario (XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(JSIDefaultScenario.ReadyScene.createSingleton(this));
    }
    
    public static class ReadyScene extends JSIScene {
        private static ReadyScene mSingleton = null;
        public static ReadyScene getSingleton() {
            assert(ReadyScene.mSingleton != null);
            return ReadyScene.mSingleton;
        }
        
        public static ReadyScene createSingleton(XScenario scenario) {
            assert(ReadyScene.mSingleton == null);
            ReadyScene.mSingleton = new ReadyScene(scenario);
            return ReadyScene.mSingleton;
        }
        
        private ReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToCreateCurPtCurve.execute(app, pt);
            XCmdToChangeScene.execute(app, 
                JSIDrawScenario.DrawScene.getSingleton(), this);
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp)this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_UP:
                    JSICmdToIncreaseStrokeWidthForCurPtCurve.execute(app, 
                            JSICanvas2D.STROKE_WIDTH_INCREASEMENT);
                    break;
                case KeyEvent.VK_DOWN:
                    JSICmdToIncreaseStrokeWidthForCurPtCurve.execute(app, 
                            -JSICanvas2D.STROKE_WIDTH_INCREASEMENT);
                    break;
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(app, 
                        JSISelectScenario.SelectReadyScene.getSingleton(), 
                        this);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.ZoomNRotateReadyScene.getSingleton(), 
                        this);
                    break;
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ColorReadyScene.getSingleton(), 
                        this);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_HOME:
                    JSICmdToHome.execute(app);
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
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
    }
}
