package jsi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.JSIXform;
import jsi.cmd.JSICmdToPan;
import jsi.cmd.JSICmdToSetStartingScreenPtForXform;
import jsi.cmd.JSICmdToZoomNRotate;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class JSINavigateScenario extends XScenario {
    private static JSINavigateScenario mSingleton = null;
    public static JSINavigateScenario getSingle() {
        assert(JSINavigateScenario.mSingleton != null);
        return JSINavigateScenario.mSingleton;
    }
    
    public static JSINavigateScenario createSingleton(XApp app) {
        assert(JSINavigateScenario.mSingleton == null);
        JSINavigateScenario.mSingleton = new JSINavigateScenario(app);
        return JSINavigateScenario.mSingleton;
    }
    
    private JSINavigateScenario (XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(JSINavigateScenario.ZoomNRotateReadyScene.
            createSingleton(this));
        this.addScene(JSINavigateScenario.ZoomNRotateScene.
            createSingleton(this));
        this.addScene(JSINavigateScenario.PanReadyScene.createSingleton(this));
        this.addScene(JSINavigateScenario.PanScene.createSingleton(this));
    }
    
    public static class ZoomNRotateReadyScene extends JSIScene {

        private static ZoomNRotateReadyScene mSingleton = null;
        public static ZoomNRotateReadyScene getSingleton() {
            assert(ZoomNRotateReadyScene.mSingleton != null);
            return ZoomNRotateReadyScene.mSingleton;
        }
        
        public static ZoomNRotateReadyScene createSingleton(
            XScenario scenario) {
            assert(ZoomNRotateReadyScene.mSingleton == null);
            ZoomNRotateReadyScene.mSingleton = 
                new ZoomNRotateReadyScene(scenario);
            return ZoomNRotateReadyScene.mSingleton;
        }
        
        private ZoomNRotateReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToSetStartingScreenPtForXform.execute(app, pt);
            XCmdToChangeScene.execute(app, 
                JSINavigateScenario.ZoomNRotateScene.getSingleton(),
                this.getReturnScene());
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
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.PanReadyScene.getSingleton(), 
                        this.getReturnScene());
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.getReturnScene(), 
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
            JSINavigateScenario scenario = (JSINavigateScenario) this.mScenario;
            scenario.drawCrosshair(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
        
    }
    
    public static class ZoomNRotateScene extends JSIScene {
        private static ZoomNRotateScene mSingleton = null;
        public static ZoomNRotateScene getSingleton() {
            assert(ZoomNRotateScene.mSingleton != null);
            return ZoomNRotateScene.mSingleton;
        }
        
        public static ZoomNRotateScene createSingleton(XScenario scenario) {
            assert(ZoomNRotateScene.mSingleton == null);
            ZoomNRotateScene.mSingleton = new ZoomNRotateScene(scenario);
            return ZoomNRotateScene.mSingleton;
        }
        
        private ZoomNRotateScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToZoomNRotate.execute(app,pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();            
            XCmdToChangeScene.execute(app, 
                JSINavigateScenario.ZoomNRotateReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_ALT:
                    Point penPt = app.getPenMarkMgr().
                        getLastPenMark().getLastPt();
                    JSICmdToSetStartingScreenPtForXform.execute(app, penPt);
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.PanScene.getSingleton(),
                        this.getReturnScene());
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_CONTROL:
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
            JSINavigateScenario scenario = (JSINavigateScenario) this.mScenario;
            scenario.drawCrosshair(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
            //JSIApp app = (JSIApp) this.mScenario.getApp();
            //app.getXform().setStartingScreenPt(null);
        }
    }
    
    public static class PanReadyScene extends JSIScene {
        private static PanReadyScene mSingleton = null;
        public static PanReadyScene getSingleton() {
            assert(PanReadyScene.mSingleton != null);
            return PanReadyScene.mSingleton;
        }
        
        public static PanReadyScene createSingleton(XScenario scenario) {
            assert(PanReadyScene.mSingleton == null);
            PanReadyScene.mSingleton = new PanReadyScene(scenario);
            return PanReadyScene.mSingleton;
        }
        
        private PanReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToSetStartingScreenPtForXform.execute(app, pt);
            
            XCmdToChangeScene.execute(app, 
                JSINavigateScenario.PanScene.getSingleton(),
                this.getReturnScene());
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
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.getReturnScene(),null);
                    break;
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.ZoomNRotateReadyScene.
                        getSingleton(), this.getReturnScene());
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
    
    public static class PanScene extends JSIScene {
        private Point mPoint = null;
        
        private static PanScene mSingleton = null;
        public static PanScene getSingleton() {
            assert(PanScene.mSingleton != null);
            return PanScene.mSingleton;
        }
        
        public static PanScene createSingleton(XScenario scenario) {
            assert(PanScene.mSingleton == null);
            PanScene.mSingleton = new PanScene(scenario);
            return PanScene.mSingleton;
        }
        
        private PanScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            mPoint = pt;
            JSICmdToPan.execute(app,pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();            
            XCmdToChangeScene.execute(app, 
                JSINavigateScenario.PanReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            switch (code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.getReturnScene(), 
                        null);
                    break;
                case KeyEvent.VK_ALT:
                    Point penPt = app.getPenMarkMgr().getLastPenMark().
                        getLastPt();
                    JSICmdToSetStartingScreenPtForXform.execute(app, penPt);
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.ZoomNRotateScene.getSingleton(), 
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
            JSINavigateScenario scenario = (JSINavigateScenario) this.mScenario;
            scenario.drawPanCrosshair(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
            //JSIApp app = (JSIApp) this.mScenario.getApp();
            //app.getXform().setStartingScreenPt(null);
        }
        
    }
    
    private void drawCrosshair (Graphics2D g2) {
        JSIScene curScene = (JSIScene)this.mApp.getScenarioMgr().getCurScene();

        double r = 30;
        Point ctr = JSIXform.PIVOT_PT;
        Line2D.Double hline = new Line2D.Double(ctr.x - r, ctr.y, 
            ctr.x + r, ctr.y);
        Line2D.Double vline = new Line2D.Double(ctr.x, ctr.y - r, 
            ctr.x, ctr.y + r);

        g2.setColor(JSICanvas2D.COLOR_CROSSHAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSSHAIR);
        g2.draw(hline);
        g2.draw(vline);
    } 
    
    private void drawPanCrosshair(Graphics2D g2) {
        JSIApp app = (JSIApp) this.getApp();
        
        Point penPt = app.getPenMarkMgr().getLastPenMark().getLastPt();
        Line2D.Double hline = new Line2D.Double(0, penPt.y,
            app.getCanvas2D().getWidth(), penPt.y);
        Line2D.Double vline = new Line2D.Double(penPt.x, 0, penPt.x,
            app.getCanvas2D().getHeight());
        
        g2.setColor(JSICanvas2D.COLOR_CROSSHAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSSHAIR);
        g2.draw(hline);
        g2.draw(vline);
    }
}
