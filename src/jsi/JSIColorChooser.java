package jsi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class JSIColorChooser {
    // constants
    public enum Area {HUE, SATURATION, OPAQUENESS}
    private static final int CELL_NUM_H = 40; // hue
    private static final int CELL_NUM_B = 10; // brightness
    
    // variables
    private Color[][] mColors = null;
    private float mSaturation = 0.8f;
    public float getSaturation() {
        return this.mSaturation;
    }    
    private float mOpaqueness = 0.7f;
    public float getOpaqueness() {
        return this.mOpaqueness;
    }
    private float mAnchorSaturation = Float.NaN;
    public void setAnchorSaturation() {
        this.mAnchorSaturation = this.mSaturation;
    }
    private float mAnchorOpaqueness = Float.NaN;
    public void setAnchorOpaqueness() {
        this.mAnchorOpaqueness = this.mOpaqueness;
    }
    
    // constructor
    public JSIColorChooser() {
        this.mColors = new Color[JSIColorChooser.CELL_NUM_B][];
        for (int i = 0; i < JSIColorChooser.CELL_NUM_B; i++) {
            this.mColors[i] = new Color[JSIColorChooser.CELL_NUM_H];
        }
        this.createCellColors();
    }

    private void createCellColors() {
        float db = 1.0f/(JSIColorChooser.CELL_NUM_B - 1); // diff btw brightness
        float dh = 1.0f/(JSIColorChooser.CELL_NUM_H - 1); // diff btw hue
        
        for (int i = 0; i < JSIColorChooser.CELL_NUM_B; i++) {
            float b = db * (float) i;
            for (int j = 0; j < JSIColorChooser.CELL_NUM_H; j++) {
                float h = dh * (float)j;
                Color hsb = Color.getHSBColor(h, this.mSaturation, b);
                this.mColors[i][j] = new Color(hsb.getRed(), hsb.getGreen(),
                    hsb.getBlue(), (int)(this.mOpaqueness*255f));
            }
        }
        
    }
    
    //recalculate the colors according to new opaqueness and saturation
    public void recalcColors(JSIPenMark penMark, int width, int height) {
        if (penMark.getPts().size() < 2) {
            return;
        }
        Point p0 = penMark.getFirstPt();
        Point p1 = penMark.getLastPt();
        
        double ys = (double)height / 3.0 * 1.0;
        double ye = (double)height / 3.0 * 2.0;
        
        if (p0.y < ys && p1.y < ys) {
            float d = (float)(p1.x - p0.x) / (float)width;
            this.mSaturation = this.mAnchorSaturation + d;
            if (this.mSaturation > 1.0f) {
                this.mSaturation = 1.0f;
            }
            else if (this.mSaturation < 0.0f) {
                this.mSaturation = 0.0f;
            }
        }
        else if (p0.y > ye && p1.y > ye) {
            float d = (float)(p1.x - p0.x) / (float)width;
            this.mOpaqueness = this.mAnchorOpaqueness + d;
            if (this.mOpaqueness > 1.0f) {
                this.mOpaqueness = 1.0f;
            }
            else if (this.mOpaqueness < 0.0f) {
                this.mOpaqueness = 0.0f;
            }
        }
        this.createCellColors();
    }
    
    public void drawCells(Graphics2D g2, int w, int h) {
        double ys = (double)h / 3.0 * 1.0;
        double ye = (double)h / 3.0 * 2.0;
        double dx = (double)w / (JSIColorChooser.CELL_NUM_H - 1);
        double dy = (ye - ys) / (JSIColorChooser.CELL_NUM_B - 1);
        
        for (int i = 0; i < JSIColorChooser.CELL_NUM_B; i++) {
            double y = ys + dy * (double)i;
            for (int j = 0; j < JSIColorChooser.CELL_NUM_H; j++) {
                double x = dx * (double)j;
                Rectangle2D rect = new Rectangle2D.Double(x, y, dx, dy);
                g2.setColor(this.mColors[i][j]);
                g2.fill(rect);
            }
        }
    }
    
    public Color calcColor(Point pt, int w, int h) {
        double ys = (double)h / 3.0 * 1.0;
        double ye = (double)h / 3.0 * 2.0;
        double dx = (double)w / (JSIColorChooser.CELL_NUM_H - 1);
        double dy = (ye - ys) / (JSIColorChooser.CELL_NUM_B - 1);
        
        int i = (int)(((double)pt.y - ys)/dy);
        int j = (int)((double)pt.x / dx);
        return this.mColors[i][j];
    }
    
    //decide which area the mouse location falles into
    public Area decideArea(Point pt, int width, int height) {
        double ys = (double) height / 3.0 * 1.0;
        double ye = (double) height / 3.0 * 2.0;
        
        if (pt.y < ys) {
            return Area.SATURATION;
        }
        else if (pt.y > ye) {
            return Area.OPAQUENESS;
        }
        else {
            return Area.HUE;
        }
    }
}
