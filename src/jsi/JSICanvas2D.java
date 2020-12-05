package jsi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class JSICanvas2D extends JPanel {
    //constants (final keywords)
    private static final Color COLOR_PT_CURVE_DEFAULT = new Color(0, 0, 0, 192);
    private static final Color COLOR_SELECTED_PT_CURVE = Color.ORANGE;
    public static final Color COLOR_SELECTION_BOX = Color.RED;
    public static final Color COLOR_CROSSHAIR = new Color(255, 0, 0, 64);
    private static final Color COLOR_INFO = new Color(255, 0, 0, 128);
    
    private static final Stroke STROKE_PT_CURVE_DEFAULT = new BasicStroke(5.0f,
        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public static final Stroke STROKE_SELECTION_BOX = new BasicStroke(5.0f);
    public static final Stroke STROKE_CROSSHAIR = new BasicStroke(5.0f);
    private static final Font FONT_INFO = 
        new Font("Monospaced", Font.PLAIN, 24);

    public static final double CROSSHAIR_RADIUS = 30;
    public static final float STROKE_WIDTH_INCREASEMENT = 1.0f;
    
    //memb variables
    private JSIApp mApp = null;
    private Stroke mCurStrokeForPtCurve = null;
    public Stroke getCurStrokeForPtCurve() {
        return this.mCurStrokeForPtCurve;
    }
    
    private Color mCurColorForPtCurve = null;
    public Color getCurColorForPtCurve() {
        return this.mCurColorForPtCurve;
    }
    public void setCurColorForPtCurve(Color c) {
        this.mCurColorForPtCurve = c;
    }
    
    //constructor
    public JSICanvas2D(JSIApp jsi) {
        this.mApp = jsi;
        this.mCurStrokeForPtCurve = JSICanvas2D.STROKE_PT_CURVE_DEFAULT;
        this.mCurColorForPtCurve = JSICanvas2D.COLOR_PT_CURVE_DEFAULT;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //changing graphics to graphics2D

        //transform the coordinate system from screen to world.
        //note : the transformation of a coordinate system is the reversed
        //process of the transformation of a geometry.
        g2.transform(this.mApp.getXform().getCurXformFromWorldToScreen());
        
        // render common world objects.
        this.drawPtCurves(g2);
        this.drawSelectedPtCurves(g2);
        this.drawCurPtCurve(g2);
        
        // render the current scene's world objects.
        JSIScene curScene = (JSIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.renderWorldOjbects(g2);
        
        // transform the coordinate system from world to screen.
        g2.transform(this.mApp.getXform().getCurXformFromScreenToWorld());
        
        // render common screen objects.
        this.drawInfo(g2);
        this.drawPenTip(g2);
        
        // render curscene objects
        curScene.renderScreenOjbects(g2);
    }

    private void drawPtCurves(Graphics2D g2) {
        for (JSIPtCurve ptCurve : this.mApp.getPtCurveMgr().getPtCurves()) {
            this.drawPtCurve(g2, ptCurve, ptCurve.getColor(), 
                ptCurve.getStroke());
        }    
    }

    private void drawCurPtCurve(Graphics2D g2) {
        JSIPtCurve ptCurve = this.mApp.getPtCurveMgr().getCurPtCurve();
        if (ptCurve != null) {
            this.drawPtCurve(g2, ptCurve, ptCurve.getColor(), 
                ptCurve.getStroke());
        }
    }

    private void drawPtCurve(Graphics2D g2, JSIPtCurve ptCurve, 
        Color c, Stroke s) {
        Path2D.Double path = new Path2D.Double();
        ArrayList<Point2D.Double> pts = ptCurve.getPts();
        if (pts.size () < 2) {
            return;
        }

        Point2D.Double pt0 = pts.get(0);
        path.moveTo(pt0.x, pt0.y);
        for (int i = 1; i < pts.size(); i++) {
            Point2D.Double pt = pts.get(i);
            path.lineTo(pt.x, pt.y);
        }

        g2.setColor(c);
        g2.setStroke(s);
        g2.draw(path);
    }

    private void drawSelectedPtCurves(Graphics2D g2) {
        for (JSIPtCurve selectedPtCurve : 
            this.mApp.getPtCurveMgr().getSelectedPtCurves()) {
            BasicStroke SPC = (BasicStroke) selectedPtCurve.getStroke();
            BasicStroke BoundCurve = new BasicStroke(SPC.getLineWidth() + 
                5.0f, SPC.getEndCap(),
                SPC.getLineJoin());
            this.drawPtCurve(g2, selectedPtCurve, 
                JSICanvas2D.COLOR_SELECTED_PT_CURVE,
                BoundCurve);
        }
        for (JSIPtCurve selectedPtCurve : 
            this.mApp.getPtCurveMgr().getSelectedPtCurves()) {
                this.drawPtCurve(g2, selectedPtCurve, 
                    selectedPtCurve.getColor(),
                    selectedPtCurve.getStroke());
        }
    }
    
    //get information of the current scene and print out
    private void drawInfo(Graphics2D g2) {
        JSIScene curScene = (JSIScene) this.mApp.getScenarioMgr().getCurScene();
        String str = curScene.getClass().getSimpleName();
        g2.setColor(JSICanvas2D.COLOR_INFO);
        g2.setFont(JSICanvas2D.FONT_INFO);
        g2.drawString(str, 20, 30);
    }

    public void increaseStrokeWidthForCurPtCurve(float f) {
        BasicStroke bs = (BasicStroke)this.mCurStrokeForPtCurve;
        float w = bs.getLineWidth();
        w += f;
        if (w < 1.0f) {
            w = 1.0f;
        }
        this.mCurStrokeForPtCurve = new BasicStroke(w, bs.getEndCap(), 
            bs.getLineJoin());
    }

    private void drawPenTip(Graphics2D g2) {
        BasicStroke bs = (BasicStroke) this.mCurStrokeForPtCurve;
        Point2D.Double worldPt0 = new Point2D.Double(0, 0);
        Point2D.Double worldPt1 = new Point2D.Double(bs.getLineWidth(), 0);
        Point2D screenPt0 = this.mApp.getXform().
            calcPtFromWorldToScreen(worldPt0);
        Point2D screenPt1 = this.mApp.getXform().
            calcPtFromWorldToScreen(worldPt1);
        double d = screenPt0.distance(screenPt1);
        double r = d/2.0;
        
        Point2D.Double ctr = new Point2D.Double(this.getWidth() - 20.0 - r,
            20.0 - r);
        Ellipse2D e = new Ellipse2D.Double(ctr.x, ctr.y, 2.0 * r, 2.0 * r);
        
        g2.setColor(this.mCurColorForPtCurve);
        g2.setStroke(this.mCurStrokeForPtCurve);
        g2.fill(e);
    }    
}
