import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Scientifical extends JFrame implements ActionListener {
    private ButtonGroup type, extra, degreeGroup;
    private JMenuBar jmb;
    private JMenu jm1, jm2, jm3;
    private JMenu jm13, jm23;
    private JMenuItem[] m2, m3, m23;
    private JRadioButtonMenuItem[] m11;
    private JCheckBoxMenuItem m12, m13;
    private JLabel jl;
    private JPanel show, buttonArea1, buttonArea2;
    private JTextField jt1, jt2;
    private JRadioButton[] degree;
    private JPanel degreeArea, mArea;
    private JButton[] M, Fun;
    private String[] fun,
            morefun = {"eˣ", "Frac", "sinh⁻¹", "sin⁻¹", "deg", "cosh⁻¹", "cos⁻¹", "2*π", "tanh⁻¹", "tan⁻¹"};
    private int[] change = {2, 10, 11, 12, 20, 21, 22, 30, 31, 32};
    private Color color = new Color(184, 207, 229);
    private String last, now, before;
    private String left, mid, right;
    private double memory = 0;
    private double l, m, r, result;
    private LinkedStack<String> op;
    private LinkedStack<Double> figure;

    public Scientifical() {
        super();
        this.setSize(520, 380);
        this.setTitle("计算器");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenu();
        showNumber();
        addButton();
        op = new LinkedStack<String>();
        figure = new LinkedStack<Double>();
        init();
        this.setVisible(true);
    }

    private void createMenu() {
        //添加菜单栏
        //查看
        m11 = new JRadioButtonMenuItem[11];
        m11[0] = new JRadioButtonMenuItem("标准型(T)");
        m11[1] = new JRadioButtonMenuItem("科学型(S)", true);
        m11[2] = new JRadioButtonMenuItem("程序员(P)");
        m11[3] = new JRadioButtonMenuItem("统计信息(A)");

        type = new ButtonGroup();//类型分组
        type.add(m11[0]);
        type.add(m11[1]);
        type.add(m11[2]);
        type.add(m11[3]);

        m12 = new JCheckBoxMenuItem("历史记录(Y)");
        m13 = new JCheckBoxMenuItem("数字分组(I)");

        m11[4] = new JRadioButtonMenuItem("基本(B)", true);
        m11[5] = new JRadioButtonMenuItem("单位转换(U)");
        m11[6] = new JRadioButtonMenuItem("日期计算(D)");
        m11[7] = new JRadioButtonMenuItem("抵押(M)");
        m11[8] = new JRadioButtonMenuItem("汽车租赁(V)");
        m11[9] = new JRadioButtonMenuItem("油耗(mpg)(F)");
        m11[10] = new JRadioButtonMenuItem("油耗(I/100 km)(U)");

        extra = new ButtonGroup();
        extra.add(m11[4]);
        extra.add(m11[5]);
        extra.add(m11[6]);
        extra.add(m11[7]);
        extra.add(m11[8]);
        extra.add(m11[9]);
        extra.add(m11[10]);

        jm13 = new JMenu("工作表(W)");
        jm13.add(m11[7]);
        jm13.add(m11[8]);
        jm13.add(m11[9]);
        jm13.add(m11[10]);

        jm1 = new JMenu("查看(V)");
        jm1.add(m11[0]);
        jm1.add(m11[1]);
        jm1.add(m11[2]);
        jm1.add(m11[3]);
        jm1.addSeparator();
        jm1.add(m12);
        jm1.add(m13);
        jm1.addSeparator();
        jm1.add(m11[4]);
        jm1.add(m11[5]);
        jm1.add(m11[6]);
        jm1.add(jm13);

        //编辑
        m2 = new JMenuItem[2];
        m2[0] = new JMenuItem("复制(C)");
        m2[1] = new JMenuItem("粘贴(V)");
        m23 = new JMenuItem[4];
        m23[0] = new JMenuItem("复制历史纪录(I)");
        m23[1] = new JMenuItem("编辑(E)");
        m23[2] = new JMenuItem("取消编辑(N)");
        m23[3] = new JMenuItem("清楚(L)");
        jm23 = new JMenu("历史记录");
        jm2 = new JMenu("编辑(E)");
        jm23.add(m23[0]);
        jm23.add(m23[1]);
        jm23.add(m23[2]);
        jm23.add(m23[3]);
        jm2.add(m2[0]);
        jm2.add(m2[1]);
        jm2.addSeparator();
        jm2.add(jm23);

        //帮助
        m3 = new JMenuItem[2];
        m3[0] = new JMenuItem("查看帮助(V)");
        m3[1] = new JMenuItem("关于计算器(A)");
        jm3 = new JMenu("帮助(H)");
        jm3.add(m3[0]);
        jm3.addSeparator();
        jm3.add(m3[1]);

        jmb = new JMenuBar();
        jmb.add(jm1);
        jmb.add(jm2);
        jmb.add(jm3);
        this.setJMenuBar(jmb);
    }

    private void showNumber() {
        jt1 = new JTextField();
        jt1.setPreferredSize(new Dimension(480, 23));
        jt1.setHorizontalAlignment(JTextField.RIGHT);
        jt1.setEditable(false);

        //显示M
        jl = new JLabel();
        jl.setPreferredSize(new Dimension(10, 35));

        jt2 = new JTextField();
        jt2.setPreferredSize(new Dimension(470, 35));
        jt2.setHorizontalAlignment(JTextField.RIGHT);
        jt2.setEditable(false);
        jt2.setText("0");
        jt2.setFont(new Font("ok", jt2.getFont().getStyle(), 20));

        show = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        show.setPreferredSize(new Dimension(485, 60));
        show.add(jt1);
        show.add(jl);
        show.add(jt2);
        jt1.setBorder(null);//隐藏边框只显示数字
        jt2.setBorder(null);
        show.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(show);
    }

    private void addButton() {
        //度、弧度、百分度
        degree = new JRadioButton[3];
        degree[0] = new JRadioButton("度", true);
        degree[1] = new JRadioButton("弧度");
        degree[2] = new JRadioButton("梯度");
        degree[0].setFocusable(false);
        degree[1].setFocusable(false);
        degree[2].setFocusable(false);
        degreeArea = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        degreeArea.setPreferredSize(new Dimension(240, 37));
        degreeGroup = new ButtonGroup();

        degreeGroup.add(degree[0]);
        degreeGroup.add(degree[1]);
        degreeGroup.add(degree[2]);

        degreeArea.add(degree[0]);
        degreeArea.add(Box.createHorizontalStrut(48));
        degreeArea.add(degree[1]);
        degreeArea.add(Box.createHorizontalStrut(48));
        degreeArea.add(degree[2]);


        degreeArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //M临时记录区
        String[] m1 = {"MC", "MR", "MS", "M+", "M-"};
        M = new JButton[5];
        mArea = new JPanel(new GridLayout(1, 5, 5, 0));
        mArea.setPreferredSize(new Dimension(240, 30));
        for (int i = 0; i < M.length; i++) {
            M[i] = new JButton(m1[i]);
            M[i].setPreferredSize(new Dimension(35, 35));
            M[i].setMargin(new Insets(0, 0, 0, 0));
            M[i].addActionListener(this);
            mArea.add(M[i]);
            M[i].setFocusable(false);
        }

        //下方按钮区
        fun = new String[]{"", "lnv", "ln", "(", ")", "←", "CE", "C", "±", "√",
                "Int", "sinh", "sin", "x²", "n!", "7", "8", "9", "/", "%",
                "dms", "cosh", "cos", "xʸ", "ʸ√x", "4", "5", "6", "*", "1/x",
                "π", "tanh", "tan", "x³", "∛x", "1", "2", "3", "-", "=",
                "F-E", "Exp", "Mod", "log", "10ˣ", "0", ".", "+"};
        Fun = new JButton[fun.length];
        buttonArea2 = new JPanel();
        buttonArea2.setPreferredSize(new Dimension(488, 205));
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        buttonArea2.setLayout(gbl);
        gbc.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < fun.length; i++) {
            Fun[i] = new JButton(fun[i]);
            Fun[i].setMargin(new Insets(0, 0, 0, 0));
            Fun[i].setPreferredSize(new Dimension(44, 36));
            Fun[i].setFocusable(false);
            Fun[i].addActionListener(this);
            if (i == 39) {
                gbc.gridheight = 2;
                gbc.gridwidth = 1;
            } else if (i == 45) {
                gbc.gridheight = 1;
                gbc.gridwidth = 2;
            } else {
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
            }
            if (i % 10 == 9) {
                gbc.gridwidth = GridBagConstraints.REMAINDER;
            }
            gbc.gridx = i % 10;
            gbc.gridy = i / 10;
            if (i > 45) gbc.gridx = i % 10 + 1;
            gbc.insets = new Insets(0, 0, 5, 5);
            if (i % 10 == 0) gbc.insets.left = 1;
            if (i % 10 == 9) gbc.insets.right = 0;
            addComponent(buttonArea2, Fun[i], gbl, gbc);
        }
        Fun[0].setEnabled(false);
        Fun[19].setEnabled(false);

        buttonArea1 = new JPanel(new GridLayout(0, 2, 5, 0));
        buttonArea1.add(degreeArea);
        buttonArea1.add(mArea);
        this.add(buttonArea1);
        this.add(buttonArea2);
    }

    private void changeButton() {
        if (Fun[1].getBackground().equals(color)) {
            Fun[1].setBackground(Fun[0].getBackground());
            for (int i = 0; i < change.length; i++) {
                Fun[change[i]].setText(fun[change[i]]);
            }
        } else {
            Fun[1].setBackground(color);
            for (int i = 0; i < change.length; i++) {
                Fun[change[i]].setText(morefun[i]);
            }
        }
    }

    private void addComponent(Container ct, Component cp, GridBagLayout gbl, GridBagConstraints gbc) {
        gbl.setConstraints(cp, gbc);
        ct.add(cp);
    }

    private void init() {
        last = "";
        now = "";
        left = "";
        mid = "";
        right = "";
        before = "";
        l = 0;
        m = 0;
        r = 0;
        op.clear();
        figure.clear();
        if (Fun[1].getBackground().equals(color)) changeButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        double num = 0;
        String number = "", string = jt2.getText();
        if (!string.equals("无效输入") && !string.equals("除数不能为0")) {
            num = Double.parseDouble(string);
            number = "" + num;
            while (number.endsWith(".") || (number.endsWith("0") && number.contains(".")))
                number = number.substring(0, number.length() - 1);
        } else if (s.equals("C") || s.equals("CE")) ;
        else return;
        if (!now.isEmpty()) last = now;
        now = s;
        switch (s) {
            case "MC":
                memory = 0;
                break;
            case "MR":
                jt2.setText("" + memory);
                break;
            case "MS":
                memory = Double.parseDouble(jt2.getText());
                break;
            case "M+":
                memory += Double.parseDouble(jt2.getText());
                break;
            case "M-":
                memory -= Double.parseDouble(jt2.getText());
                break;
            case "Int":
                if (string.contains(".")) jt2.setText(string.substring(0, string.indexOf(".")));
                if (right.isEmpty()) right = "Int(" + number + ")";
                else right = "Int(" + right + ")";
                break;
            case "Frac":
                if (string.contains(".")) jt2.setText("0" + string.substring(string.indexOf(".")));
                else jt2.setText("0");
                if (right.isEmpty()) right = "frac(" + number + ")";
                else right = "frac(" + right + ")";
                changeButton();
                break;
            case "dms":
                if (!string.contains(".")) string += ".0";
                double dms = Double.parseDouble(string.substring(string.indexOf(".")));
                dms *= 0.6;
                String string1 = "" + dms;
                jt2.setText(string.substring(0, string.indexOf(".")) + string1.substring(string1.indexOf(".")));
                if (right.isEmpty()) right = "dms(" + number + ")";
                else right = "dms(" + right + ")";
                break;
            case "deg":
                if (!string.contains(".")) string += ".0";
                double deg = Double.parseDouble(string.substring(string.indexOf("."))) / 0.6;
                deg += Double.parseDouble(string.substring(0, string.indexOf(".")));
                jt2.setText("" + deg);
                if (right.isEmpty()) right = "degrees(" + number + ")";
                else right = "degrees(" + right + ")";
                changeButton();
                break;
            case "π":
                jt2.setText("" + FastMath.PI);
                break;
            case "2*π":
                jt2.setText("" + 2 * FastMath.PI);
                changeButton();
                break;
            case "F-E":
                int count = number.length() - 1;
                if (number.contains(".")) count--;
                string = String.format("%." + count + "e", num);
                number = string.substring(0, string.indexOf("e"));
                while (number.contains(".") && number.endsWith("0"))
                    number = number.substring(0, number.length() - 1);
                if (!number.contains(".")) number += ".";
                string = number
                        + string.substring(string.indexOf("e"), string.indexOf("e") + 2)//e+或e-
                        + FastMath.abs(Integer.parseInt(string.substring(string.indexOf("e") + 1)));//10的次方
                jt2.setText(string);
                break;
            case "lnv":
                changeButton();
                break;
            case "sinh":
                jt2.setText("" + FastMath.sinh(num));
                if (right.isEmpty()) right = "sinh(" + number + ")";
                else right = "sinh(" + right + ")";
                break;
            case "sinh⁻¹":
                jt2.setText("" + FastMath.asinh(num));
                if (right.isEmpty()) right = "asinh(" + number + ")";
                else right = "asinh(" + right + ")";
                changeButton();
                break;
            case "cosh":
                jt2.setText("" + FastMath.cosh(num));
                if (right.isEmpty()) right = "cosh(" + number + ")";
                else right = "cosh(" + right + ")";
                break;
            case "cosh⁻¹":
                jt2.setText("" + FastMath.acosh(num));
                if (right.isEmpty()) right = "acosh(" + number + ")";
                else right = "acos(" + right + ")";
                changeButton();
                break;
            case "tanh":
                jt2.setText("" + FastMath.tanh(num));
                if (right.isEmpty()) right = "tanh(" + number + ")";
                else right = "tanh(" + right + ")";
                break;
            case "tanh⁻¹":
                jt2.setText("" + FastMath.atanh(num));
                if (right.isEmpty()) right = "atanh(" + number + ")";
                else right = "atanh(" + right + ")";
                changeButton();
                break;
            case "Exp":
                jt2.setText(string + (string.contains(".") ? "e+0" : ".e+0"));
                break;
            case "ln":
                jt2.setText("" + FastMath.log(num));
                if (right.isEmpty()) right = "ln(" + number + ")";
                else right = "ln(" + right + ")";
                break;
            case "eˣ":
                jt2.setText("" + FastMath.exp(num));
                if (right.isEmpty()) right = "powe(" + number + ")";
                else right = "powe(" + right + ")";
                break;
            case "sin":
                if (degree[1].isSelected()) num = FastMath.toDegrees(num);
                else if (degree[2].isSelected()) num = num * 0.9;
                if (num % 180 == 0) jt2.setText("0");
                else if (num % 360 == 30 || num % 360 == 150) jt2.setText("0.5");
                else if (num % 360 == 45 || num % 360 == 135) jt2.setText("" + FastMath.sqrt(0.5));
                else if (num % 360 == 60 || num % 360 == 120) jt2.setText("" + FastMath.sqrt(0.75));
                else if (num % 360 == 210 || num % 360 == 330) jt2.setText("-0.5");
                else if (num % 360 == 225 || num % 360 == 315) jt2.setText("-" + FastMath.sqrt(0.5));
                else if (num % 360 == 240 || num % 360 == 300) jt2.setText("-" + FastMath.sqrt(0.75));
                else jt2.setText("" + FastMath.sin(num / 180 * FastMath.PI));
                if (right.isEmpty())
                    right = "sin" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "sin" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                break;
            case "sin⁻¹":
                if (num == 0.5) jt2.setText("30");
                else if (num == FastMath.sqrt(0.5)) jt2.setText("45");
                else if (num == FastMath.sqrt(0.75)) jt2.setText("60");
                else if (num == 1) jt2.setText("90");
                else if (num == -1) jt2.setText("-90");
                else if (num == -FastMath.sqrt(0.75)) jt2.setText("-60");
                else if (num == -FastMath.sqrt(0.5)) jt2.setText("-45");
                else if (num == -0.5) jt2.setText("-30");
                else jt2.setText("" + FastMath.asin(num) * 180 / FastMath.PI);
                num = Double.parseDouble(jt2.getText());
                if (degree[1].isSelected()) jt2.setText("" + num / 180 * FastMath.PI);
                else if (degree[2].isSelected())
                    jt2.setText("" + num / 9 * 10);
                if (right.isEmpty())
                    right = "asin" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "asin" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                changeButton();
                break;
            case "cos":
                if (degree[1].isSelected()) num = FastMath.toDegrees(num);
                else if (degree[2].isSelected()) num = num * 0.9;
                if (num % 90 == 0 && num % 180 != 0) jt2.setText("0");
                else if (num % 360 == 30 || num % 360 == 330) jt2.setText("" + FastMath.sqrt(0.75));
                else if (num % 360 == 45 || num % 360 == 315) jt2.setText("" + FastMath.sqrt(2) / 2);
                else if (num % 360 == 60 || num % 360 == 300) jt2.setText("0.5");
                else if (num % 360 == 120 || num % 360 == 240) jt2.setText("-0.5");
                else if (num % 360 == 135 || num % 360 == 225) jt2.setText("-" + FastMath.sqrt(0.5));
                else if (num % 360 == 150 || num % 360 == 210) jt2.setText("-" + FastMath.sqrt(0.75));
                else jt2.setText("" + FastMath.cos(num / 180 * FastMath.PI));
                if (right.isEmpty())
                    right = "cos" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "cos" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                break;
            case "cos⁻¹":
                if (num == 0.5) jt2.setText("60");
                else if (num == FastMath.sqrt(0.5)) jt2.setText("45");
                else if (num == FastMath.sqrt(0.75)) jt2.setText("30");
                else if (num == 1) jt2.setText("0");
                else if (num == -1) jt2.setText("180");
                else if (num == -FastMath.sqrt(0.75)) jt2.setText("150");
                else if (num == -FastMath.sqrt(0.5)) jt2.setText("135");
                else if (num == -0.5) jt2.setText("120");
                else jt2.setText("" + FastMath.acos(num) * 180 / FastMath.PI);
                num = Double.parseDouble(jt2.getText());
                if (degree[1].isSelected()) jt2.setText("" + num / 180 * FastMath.PI);
                else if (degree[2].isSelected())
                    jt2.setText("" + num / 9 * 10);
                if (right.isEmpty())
                    right = "acos" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "acos" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                changeButton();
                break;
            case "tan":
                if (degree[1].isSelected()) num = FastMath.toDegrees(num);
                else if (degree[2].isSelected()) num = num * 0.9;
                if (num % 180 == 90) jt2.setText("无效输入");
                else if (num % 180 == 30) jt2.setText("" + FastMath.sqrt(1 / 3));
                else if (num % 180 == 45) jt2.setText("1");
                else if (num % 180 == 60) jt2.setText("" + FastMath.sqrt(3));
                else if (num % 180 == 120) jt2.setText("-" + FastMath.sqrt(3));
                else if (num % 180 == 135) jt2.setText("-1");
                else if (num % 180 == 150) jt2.setText("-" + FastMath.sqrt(1 / 3));
                else jt2.setText("" + FastMath.tan(num / 180 * FastMath.PI));
                if (right.isEmpty())
                    right = "tan" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "tan" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                break;
            case "tan⁻¹":
                if (num == FastMath.sqrt(1 / 3)) jt2.setText("30");
                else if (num == 1) jt2.setText("45");
                else if (num == FastMath.sqrt(3)) jt2.setText("60");
                else if (num == -FastMath.sqrt(3)) jt2.setText("-60");
                else if (num == -1) jt2.setText("-45");
                else if (num == -FastMath.sqrt(1 / 3)) jt2.setText("-30");
                else jt2.setText("" + FastMath.atan(num) * 180 / FastMath.PI);
                num = Double.parseDouble(jt2.getText());
                if (degree[1].isSelected()) jt2.setText("" + num / 180 * FastMath.PI);
                else if (degree[2].isSelected())
                    jt2.setText("" + num / 9 * 10);
                if (right.isEmpty())
                    right = "atan" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + number + ")";
                else
                    right = "atan" + (degree[0].isSelected() ? "d" : (degree[1].isSelected() ? "r" : "g")) + "(" + right + ")";
                changeButton();
                break;
            case "Mod":
                left = number;
                mid = " Mod ";
                right = "";
                break;
            case "(":
                left += mid + right + "(";
                mid = "";
                right = "";
                if (Fun[0].getText().isEmpty()) Fun[0].setText("(=1");
                else
                    Fun[0].setText("(=" + (1 + Integer.parseInt(Fun[0].getText().substring(2))));
                if (!last.isEmpty() && !(last.charAt(0) >= '0' && last.charAt(0) <= '9')) jt2.setText("0");
                op.push(s);
                break;
            case "x²":
                jt2.setText("" + num * num);
                if (right.isEmpty()) right = "sqr(" + number + ")";
                else right = "sqr" + right + ")";
                break;
            case "xʸ":
                left = number;
                mid = " ^ ";
                right = "";
                break;
            case "x³":
                jt2.setText("" + num * num * num);
                if (right.isEmpty()) right = "cube(" + number + ")";
                else right = "cube(" + right + ")";
                break;
            case "log":
                if (num > 0) jt2.setText("" + FastMath.log10(num));
                else jt2.setText("无效输入");
                if (right.isEmpty()) right = "log(" + number + ")";
                else right = "log(" + right + ")";
                break;
            case ")":
                if (!Fun[0].getText().isEmpty()) {
                    Fun[0].setText("(=" + (Integer.parseInt(Fun[0].getText().substring(2)) - 1));
                    if (right.isEmpty()) right = number;
                    if (last.equals(")")) right = "";
                    left += mid + right + ")";
                    mid = "";
                    right = "";
                    figure.push(num);
                    cal(")");
                    jt2.setText("" + figure.peek());
                }
                if (!Fun[0].getText().isEmpty() && Fun[0].getText().substring(2).equals("0")) Fun[0].setText("");
                break;
            case "n!":
                jt2.setText("" + jiecheng(num));
                if (right.isEmpty()) right = "fact(" + number + ")";
                else right = "fact(" + right + ")";
                break;
            case "ʸ√x":
                left = number;
                mid = " yroot ";
                right = "";
                break;
            case "∛x":
                jt2.setText("" + FastMath.cbrt(num));
                if (right.isEmpty()) right = "cuberoot(" + number + ")";
                else right = "cuberoot" + right + ")";
                break;
            case "10ˣ":
                jt2.setText("" + FastMath.pow(10, num));
                if (right.isEmpty()) right = "powten(" + number + ")";
                else right = "powten(" + right + ")";
                break;
            case "±":
                if (!string.equals("0")) {
                    if (string.charAt(0) != '-') jt2.setText("-" + string);
                    else jt2.setText(string.substring(1));
                }
                break;
            case "1/x":
                if (num == 0) jt2.setText("除数不能为0");
                else jt2.setText("" + 1 / num);
                if (right.isEmpty()) right = "reciproc(" + number + ")";
                else right = "reciproc(" + right + ")";
                break;
            case "√":
                jt2.setText("" + FastMath.sqrt(num));
                if (right.isEmpty()) right = "sqrt(" + number + ")";
                else right = "sqrt(" + right + ")";
                break;
            case ".":
                if (!string.endsWith(".")) jt2.setText(string + ".");
                break;
            case "←":
                if (!last.equals("=") && !string.equals("0")) {
                    jt2.setText(string.substring(0, string.length() - 1));
                    if (!jt2.getText().endsWith(".")) justify();
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (right.isEmpty()) right = number;
                if (!last.equals(")") && (!"+-*/".contains(last) || last.isEmpty())) left += mid + right;
                mid = s;
                right = "";
                break;
            case "=":
                left = "";
                mid = "";
                right = "";
                if (!last.equals("=") && !last.equals(")")) figure.push(num);
                cal("=");
                break;
            case "C":
                init();
            case "CE":
                this.jt2.setText("0");
                right = "";
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (string.equals("0") || !"1234567890.←".contains(last)) {
                    jt2.setText(s);
                    right = "";
                } else if (string.length() >= 42) ;
                else jt2.setText(string + s);
                break;
        }
        switch (s) {
            case "Mod":
            case "xʸ":
            case "ʸ√x":
            case "+":
            case "-":
            case "*":
            case "/":
                if (last.equals(")")) ;
                else if (last.equals("=")) {
                    op.pop();
                    figure.pop();
                } else if (op.isEmpty() || op.peek().equals("(")) {
                    figure.push(num);
                } else {
                    switch (op.peek()) {
                        case "Mod":
                            BigDecimal bd1 = new BigDecimal(figure.pop().toString());
                            BigDecimal bd2 = new BigDecimal(number);
                            figure.push(bd1.remainder(bd2).doubleValue());
                            op.pop();
                            break;
                        case "xʸ":
                            figure.push(FastMath.pow(figure.pop(), num));
                            op.pop();
                            break;
                        case "ʸ√x":
                            figure.push(FastMath.pow(figure.pop(), (num == 0 ? 0 : 1 / num)));
                            op.pop();
                            break;
                        case "*":
                            figure.push(figure.pop() * num);
                            op.pop();
                            break;
                        case "/":
                            figure.push(figure.pop() / num);
                            op.pop();
                            break;
                        case "+":
                            if ("+-".contains(s)) {
                                figure.push(figure.pop() + num);
                                op.pop();
                            } else {
                                figure.push(num);
                            }
                            break;
                        case "-":
                            if ("+-".contains(s)) {
                                figure.push(figure.pop() - num);
                                op.pop();
                            } else {
                                figure.push(num);
                            }
                            break;
                    }
                }
                op.push(s);
                cal();
                jt2.setText(figure.peek().toString());
                break;
        }
        if (memory != 0) jl.setText("M");
        else jl.setText("");
        if (!".←".contains(s)) justify();
        jt1.setText(left + mid + right);
    }

    private void cal(String s) {
        String operator;
        double number;
        if (s.equals(")")) {
            while (!op.peek().equals("(")) {
                switch (op.peek()) {
                    case "+":
                        figure.push(figure.pop() + figure.pop());
                        break;
                    case "-":
                        figure.push(-figure.pop() + figure.pop());
                        break;
                    case "*":
                        figure.push(figure.pop() * figure.pop());
                        break;
                    case "/":
                        figure.push(1 / figure.pop() * figure.pop());
                        break;
                }
                op.pop();
            }
            op.pop();
        } else if (s.equals("=")) {
            while (!op.isEmpty()) {
                while (!op.peek().equals("(")) {
                    number = figure.peek();
                    switch (op.peek()) {
                        case "+":
                            figure.push(figure.pop() + figure.pop());
                            break;
                        case "-":
                            figure.push(-figure.pop() + figure.pop());
                            break;
                        case "*":
                            figure.push(figure.pop() * figure.pop());
                            break;
                        case "/":
                            figure.push(1 / figure.pop() * figure.pop());
                            break;
                    }
                    operator = op.pop();
                    if (op.isEmpty()) {
                        jt2.setText("" + figure.peek());
                        op.push(operator);
                        figure.push(number);
                        return;
                    }
                }
                op.pop();
            }
        }
    }

    private void cal() {
        String string;
        if (!op.isEmpty()) {
            string = op.pop();
            while (!op.isEmpty() && "+-".contains(string)) {
                switch (op.peek()) {
                    case "+":
                        figure.push(figure.pop() + figure.pop());
                        break;
                    case "-":
                        figure.push(-figure.pop() + figure.pop());
                        break;
                    case "*":
                        figure.push(figure.pop() * figure.pop());
                        break;
                    case "/":
                        figure.push(1 / figure.pop() * figure.pop());
                        break;
                }
                if (op.peek().equals("(")) break;
                op.pop();
            }
            op.push(string);
        }
    }

    private double jiecheng(double i) {
        if (i == 0) return 1;
        if (i > 1) return i * jiecheng(i - 1);
        else return Gamma.gamma(i) * i;
    }

    private void justify() {
        String string = jt2.getText();
        if (string.isEmpty()) string = "0";
        if (string.contains("E")) string = string.replace("E", "e");
        while (string.contains(".") && !string.contains("e") && (string.endsWith("0") || string.endsWith(".")))
            string = string.substring(0, string.length() - 1);
        if (string.equals("Infinity") || string.equals("NaN")) string = "无效输入";
        jt2.setText(string);
    }

    public static void main(String[] args) {
        new Scientifical();
    }
}
