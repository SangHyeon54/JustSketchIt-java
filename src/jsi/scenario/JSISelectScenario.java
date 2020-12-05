package jsi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIPtCurve;
import jsi.JSIScene;
import jsi.JSISelectionBox;
import jsi.cmd.JSICmdToDeselectSelectedPtCurves;
import jsi.cmd.JSICmdToCreateSelectionBox;
import jsi.cmd.JSICmdToDeleteSelectedPtCurves;
import jsi.cmd.JSICmdToHome;
import jsi.cmd.JSICmdToIncreaseStrokeWidthForSelectedPtCurves;
import jsi.cmd.JSICmdToUpdateSelectedPtCurves;
import jsi.cmd.JSICmdToUpdateSelectionBox;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class JSISelectScenario extends XScenario {
    private static JSISelectScenario mSingleton = null;
    public static JSISelectScenario getSingleton() {
        assert(JSISelectScenario.mSingleton != null);
        return JSISelectScenario.mSingleton;
    }
    
    public static JSISelectScenario createSingleton(XApp app) {
        assert(JSISelectScenario.mSingleton == null);
        JSISelectScenario.mSingleton = new JSISelectScenario(app);
        return JSISelectScenario.mSingleton;
    }
    
    private JSISelectScenario (XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(JSISelectScenario.SelectReadyScene.
            createSingleton(this));
        this.addScene(JSISelectScenario.SelectScene.createSingleton(this));
        this.addScene(JSISelectScenario.SelectedReadyScene.
            createSingleton(this));
    }
    
    public static class SelectReadyScene extends JSIScene {
        private static SelectReadyScene mSingleton = null;
        public static SelectReadyScene getSingleton() {
            assert(SelectReadyScene.mSingleton != null);
            return SelectReadyScene.mSingleton;
        }
        
        public static SelectReadyScene createSingleton(XScenario scenario) {
            assert(SelectReadyScene.mSingleton == null);
            SelectReadyScene.mSingleton = new SelectReadyScene(scenario);
            return SelectReadyScene.mSingleton;
        }
        
        private SelectReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToCreateSelectionBox.execute(app, pt);
            
            XCmdToChangeScene.execute(app, 
                JSISelectScenario.SelectScene.getSingleton(), 
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
                case KeyEvent.VK_SHIFT:
                    if (!app.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                        XCmdToChangeScene.execute(app, 
                            JSISelectScenario.SelectedReadyScene.getSingleton(), 
                            null);
                    } else {
                        XCmdToChangeScene.execute(app, 
                            JSIDefaultScenario.ReadyScene.getSingleton(), 
                            null);
                    }
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
    
    public static class SelectScene extends JSIScene {
        private static SelectScene mSingleton = null;
        public static SelectScene getSingleton() {
            assert(SelectScene.mSingleton != null);
            return SelectScene.mSingleton;
        }
        
        public static SelectScene createSingleton(XScenario scenario) {
            assert(SelectScene.mSingleton == null);
            SelectScene.mSingleton = new SelectScene(scenario);
            return SelectScene.mSingleton;
        }
        
        private SelectScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToUpdateSelectionBox.execute(app, pt);
            JSICmdToUpdateSelectedPtCurves.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(app, 
                JSISelectScenario.SelectReadyScene.getSingleton(), this);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_SHIFT:
                    if (!app.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                        XCmdToChangeScene.execute(app, 
                            JSISelectScenario.SelectedReadyScene.getSingleton(), 
                            null);
                    } else {
                        XCmdToChangeScene.execute(app, 
                            JSIDefaultScenario.ReadyScene.getSingleton(), 
                            null);
                    }
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
            JSISelectScenario scenario = (JSISelectScenario) this.mScenario;
            scenario.drawSelectionBox(g2);
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
            JSISelectScenario scenario = (JSISelectScenario) this.mScenario;
            scenario.mSelectionBox = null;
        }
    }
    
    public static class SelectedReadyScene extends JSIScene {
        private static SelectedReadyScene mSingleton = null;
        public static SelectedReadyScene getSingleton() {
            assert(SelectedReadyScene.mSingleton != null);
            return SelectedReadyScene.mSingleton;
        }
        
        public static SelectedReadyScene createSingleton(XScenario scenario) {
            assert(SelectedReadyScene.mSingleton == null);
            SelectedReadyScene.mSingleton = new SelectedReadyScene(scenario);
            return SelectedReadyScene.mSingleton;
        }
        
        private SelectedReadyScene(XScenario scenario) {
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
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            int code = e.getKeyCode();
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            switch (code) {
                case KeyEvent.VK_UP:
                    JSICmdToIncreaseStrokeWidthForSelectedPtCurves.execute(app, 
                        JSICanvas2D.STROKE_WIDTH_INCREASEMENT);
                    break;
                case KeyEvent.VK_DOWN:
                    JSICmdToIncreaseStrokeWidthForSelectedPtCurves.execute(app, 
                        -JSICanvas2D.STROKE_WIDTH_INCREASEMENT);
                    break;
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(app, 
                        JSISelectScenario.SelectReadyScene.getSingleton(), 
                        this);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        JSINavigateScenario.ZoomNRotateReadyScene.
                        getSingleton(), this);
                    break;
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ColorReadyScene.getSingleton(), this);
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
                case KeyEvent.VK_ESCAPE:
                    JSICmdToDeselectSelectedPtCurves.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_DELETE:
                    JSICmdToDeleteSelectedPtCurves.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JSIDefaultScenario.ReadyScene.getSingleton(), null);
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
    
    private JSISelectionBox mSelectionBox = null;
    public JSISelectionBox getSelectionBox() {
        return mSelectionBox;
    }
    public void setSelectionBox(JSISelectionBox selectionBox) {
        this.mSelectionBox = selectionBox;
    }
    
    private void drawSelectionBox(Graphics2D g2) {
        if (this.mSelectionBox != null) {
            g2.setColor(JSICanvas2D.COLOR_SELECTION_BOX);
            g2.setStroke(JSICanvas2D.STROKE_SELECTION_BOX);
            g2.draw(this.mSelectionBox);
        }
    }
    
    public void updateSelectedPtCurves() {
        JSIApp app = (JSIApp) this.getApp();
        AffineTransform at = app.getXform().getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(
            this.mSelectionBox);
        
        ArrayList<JSIPtCurve> newlySelectedPtCurves =
            new ArrayList<JSIPtCurve>();
        for (JSIPtCurve ptCurve : app.getPtCurveMgr().getPtCurves()) {
            if (worldSelectionBoxShape.intersects(ptCurve.getBoundingBox()) || 
                ptCurve.getBoundingBox().isEmpty()) {
                for (Point2D.Double worldPt : ptCurve.getPts()) {
                    if (worldSelectionBoxShape.contains(worldPt)) {
                        newlySelectedPtCurves.add(ptCurve);
                        break;
                    }
                }
            }
        }
        app.getPtCurveMgr().getPtCurves().removeAll(newlySelectedPtCurves);
        app.getPtCurveMgr().getSelectedPtCurves().addAll(newlySelectedPtCurves);
    }
}
