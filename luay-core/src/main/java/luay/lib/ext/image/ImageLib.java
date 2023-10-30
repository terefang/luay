package luay.lib.ext.image;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class ImageLib extends AbstractLibrary implements LuayLibraryFactory
{
	public static class ImageLibHolder {
		public BufferedImage _img;
		public Graphics2D _g2d;
		Deque<Graphics2D> _queue = new ArrayDeque<>();

		public static ImageLibHolder from(int _w, int _h) {
			ImageLibHolder _ilh = new ImageLibHolder();
			_ilh._img = new BufferedImage(_w,_h, BufferedImage.TYPE_INT_ARGB);
			return _ilh;
		}

		@SneakyThrows
		public static ImageLibHolder from(File _file) {
			ImageLibHolder _ilh = new ImageLibHolder();
			_ilh._img = ImageIO.read(_file);
			return _ilh;
		}

		public void beginDraw()
		{
			if(this._g2d!=null) {
				_queue.push(this._g2d);
				this._g2d = (Graphics2D) this._g2d.create();
			}
			else
			{
				this._g2d = (Graphics2D) this._img.getGraphics();
			}
		}
		public void endDraw()
		{
			this._g2d.dispose();
			this._g2d = _queue.poll();
		}

		public void beginText()
		{
			if(this._g2d!=null) {
				_queue.push(this._g2d);
				this._g2d = (Graphics2D) this._g2d.create();
			}
			else
			{
				this._g2d = (Graphics2D) this._img.getGraphics();
			}
		}
		public void endText()
		{
			this._g2d.dispose();
			this._g2d = _queue.poll();
		}

		public void draw3DRect(int x, int y, int width, int height, boolean raised) {
			_g2d.draw3DRect(x, y, width, height, raised);
		}

		public void fill3DRect(int x, int y, int width, int height, boolean raised) {
			_g2d.fill3DRect(x, y, width, height, raised);
		}

		public void drawString(String str, int x, int y) {
			_g2d.drawString(str, x, y);
		}

		public void drawString(String str, float x, float y) {
			_g2d.drawString(str, x, y);
		}

		public void setComposite(Composite comp) {
			_g2d.setComposite(comp);
		}

		public void setPaint(Paint paint) {
			_g2d.setPaint(paint);
		}

		public void setStroke(Stroke s) {
			_g2d.setStroke(s);
		}

		public void translate(int x, int y) {
			_g2d.translate(x, y);
		}

		public void translate(double tx, double ty) {
			_g2d.translate(tx, ty);
		}

		public void rotate(double theta) {
			_g2d.rotate(theta);
		}

		public void rotate(double theta, double x, double y) {
			_g2d.rotate(theta, x, y);
		}

		public void scale(double sx, double sy) {
			_g2d.scale(sx, sy);
		}

		public void shear(double shx, double shy) {
			_g2d.shear(shx, shy);
		}

		public void setBackground(int _color) {
			_g2d.setBackground(createColor(_color));
		}

		public void clip(Shape s) {
			_g2d.clip(s);
		}

		public void setColor(long _color) {
			_g2d.setColor(createColor(_color));
		}

		public void setPaintMode() {
			_g2d.setPaintMode();
		}

		public void setFont(Font font) {
			_g2d.setFont(font);
		}

		public void clipRect(int x, int y, int width, int height) {
			_g2d.clipRect(x, y, width, height);
		}

		public void setClip(int x, int y, int width, int height) {
			_g2d.setClip(x, y, width, height);
		}

		public void drawLine(int x1, int y1, int x2, int y2) {
			_g2d.drawLine(x1, y1, x2, y2);
		}

		public void fillRect(int x, int y, int width, int height) {
			_g2d.fillRect(x, y, width, height);
		}

		public void drawRect(int x, int y, int width, int height) {
			_g2d.drawRect(x, y, width, height);
		}

		public void clearRect(int x, int y, int width, int height) {
			_g2d.clearRect(x, y, width, height);
		}

		public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
			_g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
		}

		public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
			_g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
		}

		public void drawOval(int x, int y, int width, int height) {
			_g2d.drawOval(x, y, width, height);
		}

		public void fillOval(int x, int y, int width, int height) {
			_g2d.fillOval(x, y, width, height);
		}

		public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
			_g2d.drawArc(x, y, width, height, startAngle, arcAngle);
		}

		public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
			_g2d.fillArc(x, y, width, height, startAngle, arcAngle);
		}

		public void drawPolyline(int[] points) {
			int _np = points.length/2;
			int[] _x = new int[_np];
			int[] _y = new int[_np];
			for(int _i = 0; _i<_np; _i++)
			{
				_x[_i] = points[_i*2];
				_y[_i] = points[(_i*2)+1];
			}
			_g2d.drawPolyline(_x, _y, _np);
		}

		public void drawPolygon(int[] points) {
			int _np = points.length/2;
			int[] _x = new int[_np];
			int[] _y = new int[_np];
			for(int _i = 0; _i<_np; _i++)
			{
				_x[_i] = points[_i*2];
				_y[_i] = points[(_i*2)+1];
			}
			_g2d.drawPolygon(_x, _y, _np);
		}

		public void fillPolygon(int[] points) {
			int _np = points.length/2;
			int[] _x = new int[_np];
			int[] _y = new int[_np];
			for(int _i = 0; _i<_np; _i++)
			{
				_x[_i] = points[_i*2];
				_y[_i] = points[(_i*2)+1];
			}
			_g2d.fillPolygon(_x, _y, _np);
		}

		public void gSet(int _x, int _y, long _color) { try { this._img.setRGB(_x, _y, (int)_color);}catch(Exception _xe){} }

		public void gLine(int _x1, int _y1, int _x2, int _y2, long _color)
		{
			gLine(_x1, _y1, _x2, _y2, 1, _color);
		}

		public void gLine(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			Stroke _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
			_g2d.setStroke(_s);
			_g2d.drawLine(_x1, _y1, _x2, _y2);
			endDraw();
		}

		public void gDashedLine(int _x1, int _y1, int _x2, int _y2, long _color, float _shape)
		{
			gDashedLine(_x1, _y1, _x2, _y2, 1, _color, new float[] {_shape});
		}

		public void gDashedLine(int _x1, int _y1, int _x2, int _y2, long _color, float... _shape)
		{
			gDashedLine(_x1, _y1, _x2, _y2, 1, _color, _shape);
		}

		public void gDashedLine(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color, float _shape)
		{
			gDashedLine(_x1, _y1, _x2, _y2, _lineWidth, _color, new float[] {_shape});
		}

		public void gDashedLine(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color, float... _shape)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			Stroke _dashed = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _shape, 0);
			_g2d.setStroke(_dashed);
			_g2d.drawLine(_x1, _y1, _x2, _y2);
			endDraw();
		}

		public void gRectangle(int _x1, int _y1, int _x2, int _y2, long _color)
		{
			gRectangle(false, _x1, _y1, _x2, _y2, 1, _color, null);
		}

		public void gRectangle(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color, float _dash)
		{
			gRectangle(false, _x1, _y1, _x2, _y2, _lineWidth, _color, new float[] {_dash});
		}

		public void gRectangle(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color, float[] _dash)
		{
			gRectangle(false, _x1, _y1, _x2, _y2, _lineWidth, _color, null);
		}

		public void gRectangle(boolean _fill, int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color, float[] _dash)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			if(!_fill)
			{
				Stroke _s = null;
				if(_dash == null || _dash.length==0)
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
				}
				else
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
				}
				_g2d.setStroke(_s);
				_g2d.drawRect(_x1,_y1, _x2-_x1, _y2-_y1);
			}
			else
			{
				_g2d.fillRect(_x1,_y1, _x2-_x1, _y2-_y1);
			}
			endDraw();
		}

		public void gFilledRectangle(int _x1, int _y1, int _x2, int _y2, long _color)
		{
			gRectangle(true, _x1, _y1, _x2, _y2, 1, _color, null);
		}

		public void gFilledRectangle(int _x1, int _y1, int _x2, int _y2, float _lineWidth, long _color)
		{
			gRectangle(true, _x1, _y1, _x2, _y2, _lineWidth, _color, null);
		}

		public void gPolygon(long _color, int... _points)
		{
			gPolygon(1, _color, null, _points);
		}

		public void gPolygon(float _lineWidth, long _color, float[] _dash, int... _points)
		{
			this.gPolygon(false,_lineWidth, _color, _dash, _points);
		}

		public void gPolygon(boolean _fill, float _lineWidth, long _color, float[] _dash, int... _points)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			Polygon _p = new Polygon();
			for(int _i = 0; _i<_points.length; _i+=2)
			{
				_p.addPoint(_points[_i], _points[_i+1]);
			}
			if(!_fill)
			{
				Stroke _s = null;
				if(_dash == null || _dash.length==0)
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
				}
				else
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
				}
				_g2d.setStroke(_s);
				_g2d.drawPolygon(_p);
			}
			else
			{
				_g2d.fillPolygon(_p);
			}
			endDraw();
		}

		public void gFilledPolygon(long _color, int... _points)
		{
			gFilledPolygon(1, _color, _points);
		}

		public void gFilledPolygon(float _lineWidth, long _color, int... _points)
		{
			this.gPolygon(true,_lineWidth, _color, null, _points);
		}

		public void gPolyline(long _color, int... _points)
		{
			gPolyline(1, _color, null, _points);
		}

		public void gPolyline(float _lineWidth, long _color, int... _points)
		{
			gPolyline(_lineWidth, _color, null, _points);
		}

		public void gPolyline(float _lineWidth, long _color, float[] _dash, int... _points)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			Stroke _s = null;
			if(_dash == null || _dash.length==0)
			{
				_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
			}
			else
			{
				_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
			}
			_g2d.setStroke(_s);
			int _lx = _points[0];
			int _ly = _points[1];
			for(int _i = 2; _i<_points.length; _i+=2)
			{
				_g2d.drawLine(_lx,_ly,_points[_i], _points[_i+1]);
				_lx = _points[_i];
				_ly = _points[_i+1];
			}
			endDraw();
		}

		public void gCircle(int _cx, int _cy, int _r, long _color)
		{
			this.gOval(_cx, _cy, _r, _r, 1, _color, null);
		}

		public void gCircle(int _cx, int _cy, int _r, float _lineWidth, long _color)
		{
			this.gOval(_cx, _cy, _r, _r, _lineWidth, _color, null);
		}

		public void gCircle(int _cx, int _cy, int _r, float _lineWidth, long _color, float _dash)
		{
			this.gOval(_cx, _cy, _r, _r, _lineWidth, _color, new float[]{ _dash });
		}

		public void gCircle(int _cx, int _cy, int _r, float _lineWidth, long _color, float[] _dash)
		{
			this.gOval(false, _cx, _cy, _r, _r, _lineWidth, _color, _dash);
		}

		public void gCircle(boolean _fill, int _cx, int _cy, int _r, float _lineWidth, long _color, float _dash)
		{
			this.gOval(_fill, _cx, _cy, _r, _r, _lineWidth, _color, new float[]{ _dash });
		}

		public void gCircle(boolean _fill, int _cx, int _cy, int _r, float _lineWidth, long _color, float[] _dash)
		{
			this.gOval(_fill, _cx, _cy, _r, _r, _lineWidth, _color, _dash);
		}

		public void gFilledCircle(int _cx, int _cy, int _r, long _color)
		{
			this.gCircle(true, _cx, _cy, _r, 1, _color, null);
		}

		public void gOval(int _cx, int _cy, int _ra, int _rb, long _color)
		{
			this.gOval(_cx, _cy, _ra, _rb, 1, _color, null);
		}

		public void gOval(int _cx, int _cy, int _ra, int _rb, float _lineWidth, long _color, float _dash)
		{
			this.gOval(false, _cx, _cy, _ra, _rb, _lineWidth, _color, new float[]{ _dash });
		}

		public void gOval(int _cx, int _cy, int _ra, int _rb, float _lineWidth, long _color, float[] _dash)
		{
			this.gOval(false, _cx, _cy, _ra, _rb, 1, _color, null);
		}

		public void gOval(boolean _fill, int _cx, int _cy, int _ra, int _rb, float _lineWidth, long _color, float _dash)
		{
			this.gOval(_fill, _cx, _cy, _ra, _rb, _lineWidth, _color, new float[]{ _dash });
		}

		public void gOval(boolean _fill, int _cx, int _cy, int _ra, int _rb, float _lineWidth, long _color, float[] _dash)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			if(!_fill)
			{
				Stroke _s = null;
				if(_dash == null || _dash.length==0)
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
				}
				else
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
				}
				_g2d.setStroke(_s);
				_g2d.drawOval(_cx-_ra, _cy-_rb, _ra*2,_rb*2);
			}
			else
			{
				_g2d.fillOval(_cx-_ra, _cy-_rb,_ra*2,_rb*2);
			}
			endDraw();
		}

		public void gFilledOval(int _cx, int _cy, int _ra, int _rb, long _color)
		{
			this.gOval(true, _cx, _cy, _ra, _rb, 1, _color, null);
		}

		public void gArc(int _cx, int _cy, int _ra, int _rb, int _as, int _ae, long _color)
		{
			gArc(false, _cx, _cy, _ra, _rb, _as, _ae,1, _color, null);
		}

		public void gArc(boolean _fill, int _cx, int _cy, int _ra, int _rb, int _as, int _ae, float _lineWidth, long _color, float _dash)
		{
			gArc(_fill, _cx, _cy, _ra, _rb, _as, _ae,1, _color, new float[] { _dash });
		}

		public void gArc(boolean _fill, int _cx, int _cy, int _ra, int _rb, int _as, int _ae, float _lineWidth, long _color, float[] _dash)
		{
			beginDraw();
			_g2d.setPaint(createColor(_color));
			if(!_fill)
			{
				Stroke _s = null;
				if(_dash == null || _dash.length==0)
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
				}
				else
				{
					_s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
				}
				_g2d.setStroke(_s);
				_g2d.drawArc(_cx-_ra, _cy-_rb, _ra*2,_rb*2, _as, _ae-_as);
			}
			else
			{
				_g2d.fillArc(_cx-_ra, _cy-_rb, _ra*2,_rb*2, _as, _ae-_as);
			}
			endDraw();
		}

		public void gFilledArc(int _cx, int _cy, int _ra, int _rb, int _as, int _ae, long _color)
		{
			gArc(true, _cx, _cy, _ra, _rb, _as, _ae,1, _color, null);
		}

		public void gString(String _font, int _size, int _x, int _y, String _s, long _color)
		{
			beginText();
			_g2d.setPaint(createColor(_color));
			_g2d.setFont(getFont(_font, _size));
			_g2d.drawString(_s, _x, _y);
			endText();
		}

		public void gString(Font _font, int _x, int _y, String _s, long _color)
		{
			beginText();
			_g2d.setPaint(createColor(_color));
			_g2d.setFont(_font);
			_g2d.drawString(_s, _x, _y);
			endText();
		}

		public void gString(BmpFont _font, int _x, int _y, String _s, long _color)
		{
			beginText();
			_font.drawString(this._g2d, _x, _y, _s , createColor(_color));
			endText();
		}

		public void gString(BmpFont _font, int _x, int _y, String _s, long _color, long _color2)
		{
			beginText();
			_font.drawString(this._g2d, _x, _y, _s , createColor(_color), createColor(_color2));
			endText();
		}

		public static Color createColor(long _color)
		{
			return new Color((int)_color, true);
		}

		public static final Map<String, Font> FONT_MAP = new HashMap<>();
		public static Font getFont(String _name, int _size)
		{
			String _l = String.format("%s/%d", _name, _size);
			if(!FONT_MAP.containsKey(_l))
			{
				Font _awt = Font.getFont(_name);
				if(_awt==null)
				{
					int _style = Font.PLAIN;
					if(_name.toLowerCase().contains("bold")) _style|=Font.BOLD;
					if(_name.toLowerCase().contains("italic")) _style|=Font.ITALIC;
					if(_name.toLowerCase().contains("oblique")) _style|=Font.ITALIC;
					_awt = new Font(_name, _style,_size);
				}
				else
				{
					_awt = _awt.deriveFont((float) _size);
				}
				FONT_MAP.put(_l, _awt);
			}
			return FONT_MAP.get(_l);
		}

		@SneakyThrows
		public static Font getTTFont(File _name, int _size)
		{
			String _l = String.format("%s/%d", _name.getName(), _size);
			if(!FONT_MAP.containsKey(_l))
			{
				FONT_MAP.put(_l,Font.createFont(Font.TRUETYPE_FONT, _name).deriveFont((float)_size));
			}
			return FONT_MAP.get(_l);
		}

		@SneakyThrows
		public static Font getPSFont(File _name, int _size)
		{
			String _l = String.format("%s/%d", _name.getName(), _size);
			if(!FONT_MAP.containsKey(_l))
			{
				FONT_MAP.put(_l,Font.createFont(Font.TYPE1_FONT, _name).deriveFont((float)_size));
			}
			return FONT_MAP.get(_l);
		}

		public static Font getFont(String _name, float _size)
		{
			return getFont(_name, (int)_size);
		}

		@SneakyThrows
		public static Font getTTFont(File _name, float _size)
		{
			String _l = String.format("%s/%f", _name.getName(), _size);
			if(!FONT_MAP.containsKey(_l))
			{
				FONT_MAP.put(_l,Font.createFont(Font.TRUETYPE_FONT, _name).deriveFont(_size));
			}
			return FONT_MAP.get(_l);
		}

		@SneakyThrows
		public static Font getPSFont(File _name, float _size)
		{
			String _l = String.format("%s/%f", _name.getName(), _size);
			if(!FONT_MAP.containsKey(_l))
			{
				FONT_MAP.put(_l,Font.createFont(Font.TYPE1_FONT, _name).deriveFont(_size));
			}
			return FONT_MAP.get(_l);
		}
		@SneakyThrows

		public static BmpFont getBmpFont(String _name)
		{
			if("svga1".equalsIgnoreCase(_name))
			{
				return BmpFont.svgalib_font1_8x8();
			}
			else
			if("svga2".equalsIgnoreCase(_name))
			{
				return BmpFont.svgalib_font2_8x8();
			}
			else
			if("6x11".equalsIgnoreCase(_name))
			{
				return BmpFont.default6x11();
			}
			else
			if("6x12".equalsIgnoreCase(_name))
			{
				return BmpFont.default6x12();
			}
			else
			if("7x12".equalsIgnoreCase(_name))
			{
				return BmpFont.mc6847_7x12();
			}
			else
			if("8x8".equalsIgnoreCase(_name))
			{
				return BmpFont.default8x8();
			}
			else
			if("8x12".equalsIgnoreCase(_name))
			{
				return BmpFont.tf8x12();
			}
			else
			if("8x14".equalsIgnoreCase(_name))
			{
				return BmpFont.default8x14();
			}
			else
			if("8x16".equalsIgnoreCase(_name))
			{
				return BmpFont.default8x16();
			}
			return BmpFont.defaultInstance();
		}

		public static final int ALPHA_OPAQUE = 0xff000000;
		public static final int ALPHA_MASK = 0xff000000;
		public static final int COLOR_MASK = 0x00ffffff;
		public static final int ALPHA_TRANSPARENT = 0x00000000;

		public static final int WHITE = 0xffffffff;
		public static final int BLACK = 0xff000000;

		public static final int RED = 0xffff0000;
		public static final int GREEN = 0xff00ff00;
		public static final int BLUE = 0xff0000ff;
		public static final int YELLOW = 0xffffff00;
		public static final int MAGENTA = 0xffff00ff;
		public static final int CYAN = 0xff00ffff;

		public static final int HALF_WHITE = 0x88ffffff;
		public static final int HALF_RED = 0x88ff0000;
		public static final int HALF_GREEN = 0x8800ff00;
		public static final int HALF_BLUE = 0x880000ff;
		public static final int HALF_YELLOW = 0x88ffff00;
		public static final int HALF_MAGENTA = 0x88ff00ff;
		public static final int HALF_CYAN = 0x8800ffff;
		public static final BmpFont DEFAULT_FONT = BmpFont.mc6847_7x12();
	}

	@Override
	public String getLibraryName() {
		return "image";
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("create", ImageLib::_create),
				_varArgFunctionWrapper.from("setpoint", ImageLib::_setpoint),
				_varArgFunctionWrapper.from("string", ImageLib::_string),
				_varArgFunctionWrapper.from("awtfont", ImageLib::_awtfont),
				_varArgFunctionWrapper.from("psfont", ImageLib::_psfont),
				_varArgFunctionWrapper.from("ttfont", ImageLib::_ttfont),
				_varArgFunctionWrapper.from("bmpfont", ImageLib::_bmpfont),
				_varArgFunctionWrapper.from("savepng", ImageLib::_savepng)
		);
	}

	// image.create(h, w) -> image
	@SneakyThrows
	public static LuaValue _create(Varargs args) {
		int _w = args.checkint(1);
		int _h = args.checkint(2);
		return LuaValue.userdataOf(ImageLibHolder.from(_w,_h));
	}

	// image.savepng(image) -> bool
	@SneakyThrows
	public static LuaValue _savepng(Varargs args) {
		ImageLibHolder _ctx = (ImageLibHolder)args.checkuserdata(1, ImageLibHolder.class);
		String _file = args.checkjstring(2);
		return LuaValue.valueOf(ImageIO.write(_ctx._img,"png", new File(_file)));
	}

	// image.setpoint(image, x, y, color)
	@SneakyThrows
	public static LuaValue _setpoint(Varargs args) {
		ImageLibHolder _ctx = (ImageLibHolder)args.checkuserdata(1, ImageLibHolder.class);
		int _x = args.checkint(2);
		int _y = args.checkint(3);
		long _c = args.checklong(4);
		_ctx.gSet(_x,_y,_c);
		return LuaValue.NONE;
	}

	// image.awtfont(name, size) -> font
	@SneakyThrows
	public static LuaValue _awtfont(Varargs args)
	{
		String _name = args.checkjstring(1);
		int _sz = args.checkint(2);
		return LuaValue.userdataOf(ImageLibHolder.getFont(_name, _sz));
	}

	// image.ttffont(file, size) -> font
	@SneakyThrows
	public static LuaValue _ttfont(Varargs args)
	{
		String _name = args.checkjstring(1);
		int _sz = args.checkint(2);
		return LuaValue.userdataOf(ImageLibHolder.getTTFont(new File(_name), _sz));
	}

	// image.psfont(file, size) -> font
	@SneakyThrows
	public static LuaValue _psfont(Varargs args)
	{
		String _name = args.checkjstring(1);
		int _sz = args.checkint(2);
		return LuaValue.userdataOf(ImageLibHolder.getPSFont(new File(_name), _sz));
	}

	// image.bmpfont(name) -> font
	@SneakyThrows
	public static LuaValue _bmpfont(Varargs args)
	{
		String _name = args.checkjstring(1);
		return LuaValue.userdataOf(ImageLibHolder.getBmpFont(_name));
	}

	// image.string(image, x, y, font, text[, color1[, color2]])
	@SneakyThrows
	public static LuaValue _string(Varargs args) {
		ImageLibHolder _ctx = (ImageLibHolder)args.checkuserdata(1, ImageLibHolder.class);
		int _x = args.checkint(2);
		int _y = args.checkint(3);
		LuaValue _fnt = args.arg(4);
		if(_fnt.isnil())
		{
			_fnt = LuaValue.userdataOf(ImageLibHolder.DEFAULT_FONT);
		}
		String _text = args.checkjstring(5);
		long _c1 = args.optlong(6,ImageLibHolder.BLACK);
		long _c2 = args.optlong(7,-2L);
		Object _font = _fnt.checkuserdata();
		if(_font instanceof BmpFont)
		{
			if(_c2 == -2L)
			{
				_ctx.gString((BmpFont) _font, _x, _y, _text, _c1);
			}
			else
			{
				_ctx.gString((BmpFont) _font, _x, _y, _text, _c1, _c2);
			}
		}
		else
		if(_font instanceof Font)
		{
			_ctx.gString((Font) _font, _x, _y+((Font)_font).getSize(), _text, _c1);
		}
		return LuaValue.NONE;
	}
}
