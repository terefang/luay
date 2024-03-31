package luaygui;

import com.github.terefang.jmelange.commons.util.LdataUtil;
import com.github.terefang.jmelange.commons.util.OsUtil;
import com.github.terefang.jmelange.data.ldata.LdataParser;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideComboBox;
import com.jidesoft.swing.JideTabbedPane;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import luay.main.LuayBuilder;
import luay.main.LuayContext;
import luay.vm.LuaError;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.FileUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


@Slf4j
public class MainGUI extends JFrame
{
    public static MainGUI _INSTANCE;
    private JPanel panel;

    private JTextArea _textArea;

    private JScrollPane _scroll;

    private JideComboBox _seed_box;
    private DefaultComboBoxModel _seed;
    private JideTabbedPane _tabbedPane;
    private RSyntaxTextArea _ctxArea;
    private RSyntaxTextArea _srcArea;
    private File _baseDir;
    private File _startdir;
    private File _lastdir;
    private File _srcFile;
    private File _ctxFile;
    private File _argFile;
    private List<JTextField> _argList = new Vector<>();
    private List<JTextField> _argLabel = new Vector<>();
    private ImageIcon iIcon;
    private JLabel iArea;
    private JScrollPane iScrallPanel;
    private BufferedImage iImage;

    public MainGUI() throws HeadlessException
    {
        super("LUAY GUI "+Version._VERSION);
    }

    public static int _BHEIGHT = 30;


    static final int TAB_OUTPUT_INDEX = 0;
    static final int TAB_CANVAS_INDEX = 1;
    static final int TAB_SCRIPT_INDEX = 2;
    static final int TAB_CONTEXT_INDEX = 3;
    static final int TAB_ARGUMENTS_INDEX = 4;

    @SneakyThrows
    public void init()
    {
        //this.setStartPosition(StartPosition.CenterInScreen);
        this.setSize(new Dimension(800,600));

        this.panel = new JPanel();
        this.panel.setLayout(new JideBoxLayout(this.panel, JideBoxLayout.Y_AXIS, 3));
        this.add(this.panel);

        createTabbedArea();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
    }

    private void createTabbedArea()
    {
        this._tabbedPane = new JideTabbedPane();
        this.panel.add(this._tabbedPane);
        JPanel _panel = new JPanel();
        _panel.add(new JideButton(new AbstractAction("A↑") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainGUI.this.handleIncreaseFontSize();
            }
        }));
        _panel.add(new JideButton(new AbstractAction("A↓") {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.this.handleDecreaseFontSize();
            }
        }));
        this._tabbedPane.setTabLeadingComponent(_panel);
        _panel.add(new JideButton(new AbstractAction("A←") {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.this.handleDefaultFontSize();
            }
        }));
        this._tabbedPane.setTabLeadingComponent(_panel);

        _panel = new JPanel();
        _panel.add(new JButton(new AbstractAction("Reset") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainGUI.this.handleResetTab();
            }
        }));
        _panel.add(new JButton(new AbstractAction("Load ...") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainGUI.this.handleLoadTab();
            }
        }));
        _panel.add(new JButton(new AbstractAction("Save ...") {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.this.handleSaveTab();
            }
        }));
        _panel.add(new JButton(new AbstractAction("Compile/Check") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainGUI.this.handleExecute(true);
            }
        }));
        _panel.add(new JButton(new AbstractAction("Execute") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainGUI.this.handleExecute(false);
            }
        }));
        this._tabbedPane.setTabTrailingComponent(_panel);

        this._textArea = new JTextArea(50, 132);
        this._textArea.setEditable(false);
        this._textArea.setText("Hello Luay "+Version._VERSION+"\n--\n");
        DefaultCaret caret = (DefaultCaret) this._textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this._textArea.setForeground(Color.WHITE);
        this._textArea.setBackground(Color.BLACK);
        this._textArea.setFont(createEditFont());

        this._textArea.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(final MouseEvent e)
            {
                if (SwingUtilities.isRightMouseButton(e))
                {
                    final JTextArea component = (JTextArea)e.getComponent();
                    final JPopupMenu menu = new JPopupMenu();
                    JMenuItem item = new JMenuItem(new DefaultEditorKit.CopyAction());
                    item.setText("Copy");
                    item.setEnabled(component.getSelectionStart() != component.getSelectionEnd());
                    menu.add(item);
                    item = new JMenuItem(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            MainGUI.this._textArea.setText("");
                        }
                    });
                    item.setText("Clear");
                    menu.add(item);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this._scroll = new JScrollPane(this._textArea);
        this._scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        {
            Border _etch = BorderFactory.createEtchedBorder((EtchedBorder.LOWERED));
            TitledBorder _title = BorderFactory.createTitledBorder(_etch, "OUTPUT");
            JPanel _xpanel = new JPanel();
            _xpanel.setLayout(new JideBoxLayout(_xpanel));
            _xpanel.setBorder(_title);
            _xpanel.add(this._scroll);
            this._tabbedPane.addTab("Output", _xpanel);
        }

        this.iIcon = new ImageIcon();
        this.iArea = new JLabel(this.iIcon);
        this.iScrallPanel = new JScrollPane(this.iArea);
        this.iScrallPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.iScrallPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        {
            Border _etch = BorderFactory.createEtchedBorder((EtchedBorder.LOWERED));
            TitledBorder _title = BorderFactory.createTitledBorder(_etch, "CANVAS");
            JPanel _xpanel = new JPanel();
            _xpanel.setLayout(new JideBoxLayout(_xpanel));
            _xpanel.setBorder(_title);
            _xpanel.add(this.iScrallPanel);
            this._tabbedPane.addTab("Canvas", _xpanel);
        }

        this._srcArea = new RSyntaxTextArea(50, 132);
        this._srcArea.setFont(createEditFont());
        this._srcArea.setEditable(true);
        this._srcArea.setAutoIndentEnabled(true);
        this._srcArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        this._srcArea.setText("");
        {
            Border _etch = BorderFactory.createEtchedBorder((EtchedBorder.LOWERED));
            TitledBorder _title = BorderFactory.createTitledBorder(_etch, "SCRIPT");
            JPanel _xpanel = new JPanel();
            _xpanel.setLayout(new JideBoxLayout(_xpanel));
            _xpanel.setBorder(_title);
            _xpanel.add(new RTextScrollPane(this._srcArea));
            this._tabbedPane.addTab("Script", _xpanel);
        }

        this._ctxArea = new RSyntaxTextArea(50, 132);
        this._ctxArea.setFont(createEditFont());
        this._ctxArea.setEditable(true);
        this._ctxArea.setAutoIndentEnabled(true);
        this._ctxArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON_WITH_COMMENTS);
        this._ctxArea.setText("{\n\n}");
        {
            Border _etch = BorderFactory.createEtchedBorder((EtchedBorder.LOWERED));
            TitledBorder _title = BorderFactory.createTitledBorder(_etch, "CONTEXT");
            JPanel _xpanel = new JPanel();
            _xpanel.setLayout(new JideBoxLayout(_xpanel));
            _xpanel.setBorder(_title);
            RTextScrollPane _rtsp = new RTextScrollPane(this._ctxArea);
            _xpanel.add(_rtsp);
            this._ctxArea.setVisible(false);
            _rtsp.addMouseListener(new MouseAdapter()
            {
                public void mouseReleased(final MouseEvent e)
                {
                    if (SwingUtilities.isRightMouseButton(e))
                    {
                        final RTextScrollPane component = (RTextScrollPane)e.getComponent();
                        final JPopupMenu menu = new JPopupMenu();
                        JMenuItem item = new JMenuItem(new AbstractAction("Show") {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MainGUI.this._ctxArea.setVisible(true);
                            }
                        });
                        item.setEnabled(!MainGUI.this._ctxArea.isVisible());
                        menu.add(item);
                        item = new JMenuItem(new AbstractAction("Hide") {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MainGUI.this._ctxArea.setVisible(false);
                            }
                        });
                        item.setEnabled(MainGUI.this._ctxArea.isVisible());
                        menu.add(item);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            this._tabbedPane.addTab("Context", _xpanel);
        }

        Border _etch = BorderFactory.createEtchedBorder((EtchedBorder.LOWERED));
        TitledBorder _title = BorderFactory.createTitledBorder(_etch, "_ARGS");
        _panel = new JPanel();
        _panel.setBorder(_title);
        _panel.setLayout(new JideBoxLayout(_panel, JideBoxLayout.Y_AXIS, 3));
        for(int _i=0; _i<16; _i++)
        {
            JPanel __panel = new JPanel();
            JButton _button;
            JTextField _text1;
            JTextField _text2;

            __panel.add(_text2 = new JTextField("arg label"));
            _text2.setColumns(10);
            _text2.setText("_ARG"+(_i+1));
            _argLabel.add(_text2);

            __panel.add(_text1 = new JTextField("arg"));
            _text1.setColumns(60);
            _argList.add(_text1);

            __panel.add(_button = new JButton(new AbstractAction("X") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _text1.setText("");
                }
            }));

            __panel.add(_button = new JButton(new AbstractAction("F...") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainGUI.this.handleSelectFileDir(_text1,false);
                }
            }));

            __panel.add(_button = new JButton(new AbstractAction("D...") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainGUI.this.handleSelectFileDir(_text1,true);
                }
            }));

            _panel.add(__panel);
        }

        this._tabbedPane.addTab("Arguments", _panel);
    }

    private void logClear() {
        SwingUtilities.invokeLater(()->{
            this._textArea.setText("");
            this._textArea.repaint();
        });
    }

    public synchronized void logPrint(String message)
    {
        SwingUtilities.invokeLater(()->{
            this._textArea.append(message);
            this._textArea.setCaretPosition(this._textArea.getDocument().getLength());
            this._textArea.repaint();
        });
    }

    @SneakyThrows
    public static void main(String[] args)
    {
        if(OsUtil.isWindows)
        {
            LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_VSNET);
        }

        _INSTANCE = new MainGUI();

        if(System.getProperties().containsKey("app.home"))
        {
            _INSTANCE._baseDir = new File(System.getProperty("app.home"));
            _INSTANCE._startdir=_INSTANCE._lastdir=new File(_INSTANCE._baseDir, "data");

            URL _url = new File(_INSTANCE._baseDir, "luay.png").toURL();
            Toolkit _kit = Toolkit.getDefaultToolkit();
            Image _img = _kit.createImage(_url);
            _INSTANCE.setIconImage(_img);
        }
        else
        {
            _INSTANCE._startdir=_INSTANCE._lastdir=_INSTANCE._baseDir=new File(OsUtil.getCurrentDirectory());
        }

        Handler _h = new Handler() {
            SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            @Override
            public void publish(LogRecord _record)
            {
                if(_record.getLevel() == Level.INFO)
                {
                    _INSTANCE.logPrint(_sdf.format(new Date(_record.getMillis()))+" "+Objects.toString(_record.getLevel().getName())+" "+_record.getMessage());
                }
                else
                {
                    String _thrown = null;
                    if(_record.getThrown()!=null)
                    {
                        StringWriter _sw = new StringWriter();
                        PrintWriter _pw = new PrintWriter(_sw);
                        _record.getThrown().printStackTrace(_pw);
                        _pw.flush();
                        _thrown = _sw.getBuffer().toString();
                    }
                    _INSTANCE.logPrint(_sdf.format(new Date(_record.getMillis()))+" "+Objects.toString(_record.getLevel().getName())+" "
                            +_record.getSourceClassName()
                            +"@"
                            +_record.getSourceMethodName()
                            +" "
                            +_record.getMessage()
                            +(_thrown!=null ? "\n"+_thrown : "")
                    );
                }
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

        Logger.getGlobal().addHandler(_h);
        Logger.getAnonymousLogger().addHandler(_h);
        Logger.getLogger("").addHandler(_h);

        _INSTANCE.init();
        _INSTANCE.pack();
        _INSTANCE.setVisible(true);
    }

    static Font _editfont = null;
    @SneakyThrows
    static public synchronized Font createEditFont(float _size)
    {
        if(_editfont==null)
        {
            _editfont = Font.createFont(Font.TRUETYPE_FONT,
                    ClasspathResourceLoader.of("JetBrainsMono-Regular.ttf", null).getInputStream())
            ;
        }
        return _editfont.deriveFont(_size);
    }
    @SneakyThrows
    static public synchronized Font createEditFont()
    {
        return createEditFont(12f);
    }

    static Font _regfont = null;
    static Font _boldfont = null;
    static Font _italicfont = null;
    static Font _bolditalicfont = null;
    static Map<String,Font> _font = new HashMap<>();


    @SneakyThrows
    static public synchronized Font createFont(String _file, float _size)
    {
        String _key = String.format("%s-0", _file);
        if(_font.containsKey(_key))
        {
            Font _ffont = Font.createFont(Font.TRUETYPE_FONT,
                    ClasspathResourceLoader.of(_file, null).getInputStream());
            _font.put(_key, _ffont);
        }
        return _font.get(_key).deriveFont(_size);

    }

    @SneakyThrows
    static public synchronized Font createDefaultFont(String[] _families, boolean _bold, boolean _italic, float _size)
    {
        String _key = String.format("%s-%s-%s-%d", _families[0], _bold, _italic, (int) _size);

        if (_font.containsKey(_key)) {
            return _font.get(_key);
        }

        if ("monospace".equalsIgnoreCase(_families[0]))
        {
            return createEditFont().deriveFont(_size);
        }

        if(_bold)
        {
            if(_italic)
            {
                if(_bolditalicfont==null)
                {
                    _bolditalicfont = Font.createFont(Font.TRUETYPE_FONT,
                            ClasspathResourceLoader.of("NunitoSans-BoldItalic.ttf", null).getInputStream());
                }

                _font.put(_key, _bolditalicfont.deriveFont(_size));
            }
            else
            {
                if(_boldfont==null)
                {
                    _boldfont = Font.createFont(Font.TRUETYPE_FONT,
                            ClasspathResourceLoader.of("NunitoSans-Bold.ttf", null).getInputStream());
                }
                _font.put(_key, _boldfont.deriveFont(_size));
            }
        }
        else
        {
            if(_italic)
            {
                if(_italicfont==null)
                {
                    _italicfont = Font.createFont(Font.TRUETYPE_FONT,
                            ClasspathResourceLoader.of("NunitoSans-Italic.ttf", null).getInputStream());
                }
                _font.put(_key,  _italicfont.deriveFont(_size));
            }
            else
            {
                if(_regfont==null)
                {
                    _regfont = Font.createFont(Font.TRUETYPE_FONT,
                            ClasspathResourceLoader.of("NunitoSans-Regular.ttf", null).getInputStream());
                }
                _font.put(_key, _regfont.deriveFont(_size));
            }
        }
        return _font.get(_key);
    }

    public void handleIncreaseFontSize() {
        JComponent _comp = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                _comp = this._textArea;
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                _comp = this._srcArea;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                _comp = this._ctxArea;
                break;
            }
            default:
                return;
        }

        int _size = _comp.getFont().getSize();
        _comp.setFont(_comp.getFont().deriveFont((float)(2f+_size)));
    }

    public void handleDecreaseFontSize() {
        JComponent _comp = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                _comp = this._textArea;
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                _comp = this._srcArea;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                _comp = this._ctxArea;
                break;
            }
            default:
                return;
        }

        int _size = _comp.getFont().getSize();
        _comp.setFont(_comp.getFont().deriveFont((float)(_size-2f)));
    }

    public void handleDefaultFontSize() {
        JComponent _comp = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                _comp = this._textArea;
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                _comp = this._srcArea;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                _comp = this._ctxArea;
                break;
            }
            default:
                return;
        }

        _comp.setFont(_comp.getFont().deriveFont(12f));
    }

    public void handleResetTab()
    {
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                this._textArea.setText("");
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                this._srcArea.setText("");
                break;
            }
            case TAB_CANVAS_INDEX:
            {
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                this._ctxArea.setVisible(false);
                this._ctxArea.setText("{\n\n}\n");
                break;
            }
            case TAB_ARGUMENTS_INDEX:
            {
                for(int _i=0; _i<this._argList.size(); _i++)
                {
                    JTextField _fv = this._argList.get(_i);
                    JTextField _fk = this._argLabel.get(_i);

                    _fk.setText("_ARG"+(_i+1));
                    _fv.setText("");
                }
                break;
            }
            default:
                return;
        }
    }

    @SneakyThrows
    public BufferedImage createCanvas(int w, int h)
    {
        final BufferedImage _img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        this.iImage = _img;
        updateCanvas();
        return _img;
    }

    public void setCanvas(BufferedImage _img)
    {
        this.iImage = _img;
        updateCanvas();
    }

    public BufferedImage getCanvas()
    {
        updateCanvas();
        return this.iImage;
    }

    private void updateCanvas()
    {
        SwingUtilities.invokeLater( () -> {
            try
            {
                this._tabbedPane.setSelectedIndex(TAB_CANVAS_INDEX);
                this.iIcon.setImage(this.iImage);
                this.iArea.setVisible(true);
                this.iArea.revalidate();
                this.iScrallPanel.getViewport().setViewPosition(new Point(0, 0));;
                this.iScrallPanel.repaint();
                this.repaint();
            }
            catch (Exception _xe) {  }
        });
    }

    @SneakyThrows
    public void handleLoadTab()
    {
        File _file = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_SCRIPT_INDEX:
            {
                _file = this._srcFile;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                this._ctxArea.setVisible(false);
                _file = this._ctxFile;
                break;
            }
            case TAB_ARGUMENTS_INDEX:
            {
                _file = this._argFile;
                break;
            }
            default:
                return;
        }

        JFileChooser _j = new JFileChooser();

        _j.setCurrentDirectory(this._lastdir);

        if(_file != null) _j.setSelectedFile(_file);

        _j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter[] _fl = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_SCRIPT_INDEX:
            {
                _fl = _luay_exts;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                _fl = _ctx_exts;
                break;
            }
            case TAB_ARGUMENTS_INDEX:
            {
                _fl = _ctx_exts;
                break;
            }
            default:
                return;
        }

        if(_fl!=null)
        for (FileNameExtensionFilter _f : _fl)
        {
            _j.addChoosableFileFilter(_f);
            _j.setFileFilter(_f);
        }
        int _opt = _j.showOpenDialog(this);
        if(_opt == JFileChooser.APPROVE_OPTION)
        {
            _file = _j.getSelectedFile();
            this._lastdir = _file.getParentFile();
            switch(this._tabbedPane.getSelectedIndex())
            {
                case TAB_SCRIPT_INDEX:
                {
                    this._srcArea.setText(IOUtils.toString(_file.toURL(), StandardCharsets.UTF_8));
                    this._srcFile = _file;
                    this._tabbedPane.setTitleAt(TAB_SCRIPT_INDEX,"Script");
                    break;
                }
                case TAB_CONTEXT_INDEX:
                {
                    this._ctxArea.setText(IOUtils.toString(_file.toURL(), StandardCharsets.UTF_8));
                    this._ctxFile = _file;
                    this._tabbedPane.setTitleAt(TAB_CONTEXT_INDEX,"Context");
                    break;
                }
                case TAB_ARGUMENTS_INDEX:
                {
                    Map<String, Object> _list = LdataUtil.loadFrom(_file);
                    List<String> _set = new Vector(_list.keySet());
                    for(int _i=0; _i<this._argList.size() && _i<_set.size(); _i++)
                    {
                        this._argLabel.get(_i).setText(_set.get(_i));
                        this._argList.get(_i).setText(_list.get(_set.get(_i)).toString());
                    }
                    this._argFile = _file;
                    break;
                }
                default:
                    return;
            }
        }
    }
    @SneakyThrows
    private void handleSaveTab() {
        File _file = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                _file = this._srcFile;
                break;
            }
            case TAB_CONTEXT_INDEX:
            {
                _file = this._ctxFile;
                break;
            }
            case TAB_ARGUMENTS_INDEX:
            {
                _file = this._argFile;
                break;
            }
            default:
                return;
        }

        JFileChooser _j = new JFileChooser();

        _j.setCurrentDirectory(this._lastdir);

        if(_file != null) _j.setSelectedFile(_file);

        _j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter[] _fl = null;
        switch(this._tabbedPane.getSelectedIndex())
        {
            case TAB_OUTPUT_INDEX:
            {
                break;
            }
            case TAB_CANVAS_INDEX:
            {
                _fl = _img_exts;
                break;
            }
            case TAB_SCRIPT_INDEX:
            {
                _fl = _luay_exts;
                break;
            }
            case TAB_CONTEXT_INDEX:
            case TAB_ARGUMENTS_INDEX:
            {
                _fl = _ctx_exts;
                break;
            }
            default:
                return;
        }

        if(_fl!=null)
        {
            for (FileNameExtensionFilter _f : _fl)
            {
                _j.addChoosableFileFilter(_f);
                _j.setFileFilter(_f);
            }
        }

        int _opt = _j.showSaveDialog(this);
        if(_opt == JFileChooser.APPROVE_OPTION)
        {
            _file = _j.getSelectedFile();
            this._lastdir = _file.getParentFile();
            switch(this._tabbedPane.getSelectedIndex())
            {
                case TAB_OUTPUT_INDEX:
                {
                    Files.writeString(_file.toPath(), this._textArea.getText(), StandardCharsets.UTF_8);
                    break;
                }
                case TAB_SCRIPT_INDEX:
                {
                    Files.writeString(_file.toPath(), this._srcArea.getText(), StandardCharsets.UTF_8);
                    this._srcFile = _file;
                    this._tabbedPane.setTitleAt(TAB_SCRIPT_INDEX,"Script");
                    break;
                }
                case TAB_CANVAS_INDEX:
                {
                    String _ext = FileUtils.extension(_file.getName());
                    ImageIO.write(this.iImage,_ext,_file);
                    this._tabbedPane.setTitleAt(TAB_CANVAS_INDEX,"Canvas");
                    break;
                }
                case TAB_CONTEXT_INDEX:
                {
                    Files.writeString(_file.toPath(), this._ctxArea.getText(), StandardCharsets.UTF_8);
                    this._ctxFile = _file;
                    this._tabbedPane.setTitleAt(TAB_CONTEXT_INDEX,"Context");
                    break;
                }
                case TAB_ARGUMENTS_INDEX:
                {
                    Map<String,Object> _list = new HashMap<>();
                    for(int _i=0; _i<this._argList.size(); _i++)
                    {
                        JTextField _fv = this._argList.get(_i);
                        JTextField _fk = this._argLabel.get(_i);

                        if(_fk.getText().trim().length()!=0)
                        {
                            _list.put(_fk.getText().trim(), _fv.getText().trim());
                        }
                    }

                    LdataUtil.writeTo(_list,_file);
                    this._argFile = _file;
                    break;
                }
                default:
                    return;
            }
        }
    }

    private void handleSelectFileDir(JTextField _text, boolean _dirOnly)
    {
        JFileChooser _j = new JFileChooser();
        _j.setCurrentDirectory(this._lastdir);
        if(_dirOnly)
        {
            _j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        else
        {
            _j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        int _opt = _j.showDialog(this, "Select");
        if(_opt == JFileChooser.APPROVE_OPTION)
        {
            File _file = _j.getSelectedFile();
            this._lastdir = _file.getParentFile();
            _text.setText(_file.getAbsolutePath());
        }
    }

    private void handleExecute(boolean _compileOnly)
    {
        SwingUtilities.invokeLater(()->{
            this._tabbedPane.setSelectedIndex(0);
        });

        LuayContext _ctx = buildContaxt();

        for(Map.Entry<String, Object> _entry : LdataParser.loadFrom(new StringReader(this._ctxArea.getText())).entrySet())
        {
            _ctx.set(_entry.getKey(), _entry.getValue());
        };

        List<String> _args = new Vector<>();
        for(JTextField _f : this._argList)
        {
            if(_f.getText().trim().length()==0) break;
            _args.add(_f.getText().trim());
        }
        _ctx.set("_ARGS", _args);

        for(int _i=0; _i<this._argList.size(); _i++)
        {
            JTextField _fv = this._argList.get(_i);
            JTextField _fk = this._argLabel.get(_i);

            if(_fk.getText().trim().length()!=0 && _fv.getText().trim().length()!=0)
            {
                _ctx.set(_fk.getText().trim(), _fv.getText().trim());
            }
        }

        try {
            File _file = this._srcFile;
            String _name = _file == null ? "unnamed": _file.getName();
            this.logPrint("- - - "+(_compileOnly ? "check" : "exec")+": "+_name+"\n");
            if(_file != null)
            {
                _ctx.setBase(_file);
            }
            _ctx.compileScript(this._srcArea.getText(), _name);
            if(_compileOnly)
            {
                this.logPrint("- - - OK.\n");
            }
            else
            {
                _ctx.runScript();
                this.logPrint("- - - finish.\n");
            }
        }
        catch(LuaError _lxe)
        {
            this.logPrint(_lxe.getMessage()+"\n");
            for(StackTraceElement _st :_lxe.getStackTrace())
            {
                this.logPrint("file = "+_st.getFileName()+", line = "+_st.getLineNumber()+"\n");
            }
        }
        catch(Exception _xe)
        {
            this.logPrint(_xe.toString()+"\n");
        }
    }

    LuayContext buildContaxt()
    {
        return LuayBuilder.create()
                .errorStream(new OutputStream() {
                    int _n=0;
                    byte[] _buf = new byte[8192];
                    @Override
                    public void write(int b) throws IOException
                    {
                        _buf[_n++] = (byte) (b & 0xff);
                        if((b == 13) || (b == 10) || ((b<127) && _n>20) || (_n==_buf.length))
                        {
                            MainGUI.this.logPrint(new String(_buf,0, _n, StandardCharsets.UTF_8));
                            _n=0;
                        }
                    }
                })
                .outputStream(new OutputStream() {
                    int _n=0;
                    byte[] _buf = new byte[8192];
                    @Override
                    public void write(int b) throws IOException
                    {
                        _buf[_n++] = (byte) (b & 0xff);
                        if((b == 13) || (b == 10) || ((b<127) && _n>20) || (_n==_buf.length))
                        {
                            MainGUI.this.logPrint(new String(_buf,0, _n, StandardCharsets.UTF_8));
                            _n=0;
                        }
                    }
                })
                .baseLibraries()
                .noDefaultSearchPaths(true)
                .searchPath(this._baseDir.getAbsolutePath() + "/luay")
                .searchPath(OsUtil.getUserConfigDirectory("luay"))
                .searchPath(OsUtil.getUserDataDirectory("luay"))
                .searchPath(OsUtil.getUnixyUserDataDirectory("luay"))
                .searchPath(OsUtil.getSystemDataDirectory("luay"))
                .build();
    }

    static FileNameExtensionFilter[] _img_exts = null;
    static {
        ImageIO.scanForPlugins();

        String[] _suf = ImageIO.getReaderFileSuffixes();
        _img_exts = new FileNameExtensionFilter[_suf.length+1];
        for(int _i = 0; _i< _suf.length; _i++)
        {
            _img_exts[_i] = new FileNameExtensionFilter(_suf[_i].toUpperCase()+" Images (*."+_suf[_i]+")", _suf[_i].toLowerCase(), _suf[_i].toUpperCase());
        }
        _img_exts[_suf.length] = new FileNameExtensionFilter("All Images", _suf);
    }
    static FileNameExtensionFilter[] _luay_exts = {
            new FileNameExtensionFilter("Luay Script (*.lua)", "lua"),
    };

    static FileNameExtensionFilter[] _ctx_exts = {
            new FileNameExtensionFilter("Context Data (*.ldata, *.pdata, *.json, *.hjson, *.hson)", "ldata", "pdata", "json", "hjson", "hson"),
    };

}
