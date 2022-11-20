import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GUI extends JFrame{
    private JPanel file_panel, mainPanel,panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel_gen;
    private JButton b_stat_file, b_dyn_file, b1, b2, b3, b4, b5, b6, b7, b_gen;
    private JButton b1_confirm, b2_confirm, b3_confirm, b4_confirm,b5_confirm, b6_confirm,b7_confirm, b_gen_confirm;
    private JButton b_main;
    private Program mp = new Program();
    private JTextField nemocnice_num;
    private JTextField pacienti_num;
    private JTextField hospitalizacie_num;
    private JTextArea label_logs;
    private JTextField rod_cislo;
    private JTextField meno;
    private JTextField priezvisko;
    private JTextField nemocnica;
    private JTextField nemocnica_nahrada;
    private JTextField poistovna;
    private JFormattedTextField d_narodenia;
    private JFormattedTextField d_hosp;
    private JFormattedTextField d_mesiac_rok;
    private JFormattedTextField d_hosp_od;
    private JFormattedTextField d_hosp_do;
    private JFormattedTextField d_k_hosp;
    private JTextField diagnoza;

    private JTextField pacienti_input;
    private JTextField pacienti_output;
    private JTextField nemocnice_input;
    private JTextField nemocnice_output;
    boolean staticFile = false;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GUI() {
        //createMainPanel();
        //addMainPanel();
        createStartPanel();
        this.setTitle("Aplikacia");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500,1000);
        this.setVisible(true);
    }
    public Program getMP() {
        return mp;
    }
    private void createPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,80, 25);
        panel1.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(100,20,165,25);
        panel1.add(rod_cislo);

        /*
        JLabel label_nem = new JLabel("Nemocnica");
        label_nem.setBounds(10, 70,80, 25);
        panel1.add(label_nem );

        nemocnica = new JTextField(15);
        nemocnica.setBounds(100,70,165,25);
        panel1.add(nemocnica);
        */

        label_logs = new JTextArea();
        label_logs.setBounds(400, 20,800, 700);
        label_logs.setEditable(false);
        JScrollPane scroll = new JScrollPane(label_logs,   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(400, 20,800, 700);
        scroll.setPreferredSize(new Dimension(300, 50));

        label_logs.setLineWrap(true);
        panel1.add(label_logs);

        panel1.add(scroll);
        scroll.setViewportView(label_logs);

        panel1.add(b_main);
        panel1.add(b1_confirm);

    }
    private void createPanel2() {
        panel2 = new JPanel();
        panel2.setLayout(null);

        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,80, 25);
        panel2.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(100,20,165,25);
        panel2.add(rod_cislo);

        //TODO : add import hospitalizacieId

        label_logs = new JTextArea();
        label_logs.setBounds(400, 20,800, 700);
        label_logs.setEditable(false);
        JScrollPane scroll = new JScrollPane(label_logs,   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(400, 20,800, 700);
        scroll.setPreferredSize(new Dimension(300, 50));

        label_logs.setLineWrap(true);
        panel2.add(label_logs);

        panel2.add(scroll);
        scroll.setViewportView(label_logs);

        panel2.add(b_main);
        panel2.add(b2_confirm);

    }

    private void createPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(null);
        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,130, 25);
        panel3.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(150,20,165,25);
        panel3.add(rod_cislo);
        /*
        JLabel label_nem = new JLabel("Nemocnica");
        label_nem.setBounds(10, 70,130, 25);
        panel3.add(label_nem );

        nemocnica = new JTextField(15);
        nemocnica.setBounds(150,70,165,25);
        panel3.add(nemocnica);
        */
        JLabel label_d_hosp = new JLabel("Datum Hospitalizacie");
        label_d_hosp.setBounds(10, 120,130, 25);
        panel3.add(label_d_hosp );

        d_hosp = new JFormattedTextField(formatter);
        LocalDateTime date = LocalDateTime.now();
        d_hosp.setText(date.format(formatter));
        d_hosp.setBounds(150,120,165,25);
        panel3.add(d_hosp);

        JLabel label_diagnoza = new JLabel("Diagnoza");
        label_diagnoza.setBounds(10, 170,130, 25);
        panel3.add(label_diagnoza);

        diagnoza = new JTextField(10);
        diagnoza.setBounds(150,170,165,25);
        panel3.add(diagnoza);

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel3.add(label_logs);

        panel3.add(b_main);
        panel3.add(b3_confirm);

    }
    private void createPanel4() {
        panel4 = new JPanel();
        panel4.setLayout(null);
        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,130, 25);
        panel4.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(150,20,165,25);
        panel4.add(rod_cislo);
/*
        JLabel label_nem = new JLabel("Nemocnica");
        label_nem.setBounds(10, 70,130, 25);
        panel4.add(label_nem );

        nemocnica = new JTextField(15);
        nemocnica.setBounds(150,70,165,25);
        panel4.add(nemocnica);
*/
        JLabel label_d_hosp = new JLabel("Koniec Hospitalizacie");
        label_d_hosp.setBounds(10, 120,130, 25);
        panel4.add(label_d_hosp );


        d_k_hosp = new JFormattedTextField(formatter);
        LocalDateTime date = LocalDateTime.now();
        d_k_hosp.setText(date.format(formatter));
        d_k_hosp.setBounds(150,120,165,25);
        panel4.add(d_k_hosp);

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel4.add(label_logs);


        panel4.add(b_main);
        panel4.add(b4_confirm);
    }
    private void createPanel5() {
        panel5 = new JPanel();
        panel5.setLayout(null);
        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,130, 25);
        panel5.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(150,20,165,25);
        panel5.add(rod_cislo);

        JLabel label_meno = new JLabel("Meno");
        label_meno.setBounds(10, 70,130, 25);
        panel5.add(label_meno );

        meno = new JTextField(10);
        meno.setBounds(150,70,165,25);
        panel5.add(meno);

        JLabel label_priezvisko = new JLabel("Priezvisko");
        label_priezvisko.setBounds(10, 120,130, 25);
        panel5.add(label_priezvisko );

        priezvisko = new JTextField(15);
        priezvisko.setBounds(150,120,165,25);
        panel5.add(priezvisko);

        JLabel label_d_narodenia = new JLabel("Datum Narodenia");
        label_d_narodenia.setBounds(10, 170,130, 25);
        panel5.add(label_d_narodenia );

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        d_narodenia = new JFormattedTextField(df);
        d_narodenia.setBounds(150,170,165,25);
        panel5.add(d_narodenia);

        JLabel label_poistovna = new JLabel("Poistovna");
        label_poistovna.setBounds(10, 220,130, 25);
        panel5.add(label_poistovna );

        poistovna = new JTextField(10);
        poistovna.setBounds(150,220,165,25);
        panel5.add(poistovna);

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel5.add(label_logs);

        panel5.add(b_main);
        panel5.add(b5_confirm);

    }
    private void createPanel6() {
        panel6 = new JPanel();
        panel6.setLayout(null);
        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,130, 25);
        panel6.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(150,20,165,25);
        panel6.add(rod_cislo);

        // TODO Add input for hospId

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel6.add(label_logs);

        panel6.add(b_main);
        panel6.add(b6_confirm);
    }
    private void createPanel7() {
        panel7 = new JPanel();
        panel7.setLayout(null);

        JLabel label_rc = new JLabel("Rod. Cislo");
        label_rc.setBounds(10, 20,130, 25);
        panel7.add(label_rc);

        rod_cislo = new JTextField(10);
        rod_cislo.setBounds(150,20,165,25);
        panel7.add(rod_cislo);

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel7.add(label_logs);

        panel7.add(b_main);
        panel7.add(b7_confirm);

    }

    private void createPanel_Gen() {
        panel_gen = new JPanel();
        panel_gen.setLayout(null);

        JLabel label_nem = new JLabel("Pocet Nemocnic");
        label_nem.setBounds(10, 20,120, 25);
        panel_gen.add(label_nem );

        nemocnice_num = new JTextField(15);
        nemocnice_num.setBounds(150,20,165,25);
        panel_gen.add(nemocnice_num);

        JLabel label_pac = new JLabel("Pocet Pacientov");
        label_pac.setBounds(10, 70,120, 25);
        panel_gen.add(label_pac);

        pacienti_num = new JTextField(15);
        pacienti_num.setBounds(150,70,165,25);
        panel_gen.add(pacienti_num);

        JLabel label_hosp = new JLabel("Pocet Hospitalizacii");
        label_hosp.setBounds(10, 120,120, 25);
        panel_gen.add(label_hosp);

        hospitalizacie_num = new JTextField(15);
        hospitalizacie_num.setBounds(150,120,165,25);
        panel_gen.add(hospitalizacie_num);

        label_logs = new JTextArea();
        label_logs.setBounds(35, 330,300, 50);
        label_logs.setEditable(false);
        panel_gen.add(label_logs);

        panel_gen.add(b_main);
        panel_gen.add(b_gen_confirm);
    }
    private void createStartPanel() {

        file_panel = new JPanel();
        file_panel.setLayout(null);

        b_stat_file = new JButton("Static file");
        b_stat_file.addActionListener(new addButtonListener());
        b_stat_file.setBounds(20, 100, 400, 40);
        file_panel.add(b_stat_file);

        b_dyn_file = new JButton("Dynamic file");
        b_dyn_file.addActionListener(new addButtonListener());
        b_dyn_file.setBounds(20, 200, 400, 40);
        file_panel.add(b_dyn_file);

        add(file_panel);

    }
    private void createMainPanel() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        createButtons();

    }
    private void createButtons() {
        b_main = new JButton("Main screen");
        b_main.addActionListener(new addButtonListener());
        b_main.setBounds(50, 400, 120, 30);

        int counter = 1;

        b1 = new JButton("#1 Vyhladanie zaznamov pacienta");
        b1.addActionListener(new addButtonListener());
        b1.setBounds(20, counter *50, 400, 40);
        b1_confirm = new JButton("B1_CONFIRM");
        b1_confirm.addActionListener(new addButtonListener());
        b1_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b2 = new JButton("#2 Vyhladanie hospitalizacie");
        b2.addActionListener(new addButtonListener());
        b2.setBounds(20, counter *50, 400, 40);
        b2_confirm = new JButton("B2_CONFIRM");
        b2_confirm.addActionListener(new addButtonListener());
        b2_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b3 = new JButton("#3 Zacat hospitalizaciu");
        b3.addActionListener(new addButtonListener());
        b3.setBounds(20, counter *50, 400, 40);
        b3_confirm = new JButton("B3_CONFIRM");
        b3_confirm.addActionListener(new addButtonListener());
        b3_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b4 = new JButton("#4 Ukoncit hospitalizaciu");
        b4.addActionListener(new addButtonListener());
        b4.setBounds(20, counter *50, 400, 40);
        b4_confirm = new JButton("B4_CONFIRM");
        b4_confirm.addActionListener(new addButtonListener());
        b4_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b5 = new JButton("#5 Pridat pacienta");
        b5.addActionListener(new addButtonListener());
        b5.setBounds(20, counter *50, 400, 40);
        b5_confirm = new JButton("B5_CONFIRM");
        b5_confirm.addActionListener(new addButtonListener());
        b5_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b6 = new JButton("#6 Vymazanie hospitalizacie");
        b6.addActionListener(new addButtonListener());
        b6.setBounds(20, counter *50, 400, 40);
        b6_confirm = new JButton("B6_CONFIRM");
        b6_confirm.addActionListener(new addButtonListener());
        b6_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b7 = new JButton("#7 Vymazanie pacienta");
        b7.addActionListener(new addButtonListener());
        b7.setBounds(20, counter *50, 400, 40);
        b7_confirm = new JButton("B7_CONFIRM");
        b7_confirm.addActionListener(new addButtonListener());
        b7_confirm.setBounds(200, 400, 120, 30);
        counter++;

        b_gen = new JButton("Generate Data");
        b_gen.addActionListener(new addButtonListener());
        b_gen.setBounds(250, counter *50 + 20, 400, 40);
        b_gen_confirm = new JButton("B_GEN_CONFIRM");
        b_gen_confirm.addActionListener(new addButtonListener());
        b_gen_confirm.setBounds(200, 400, 120, 30);
    }

    private void addMainPanel() {
        mainPanel.add(b1);
        mainPanel.add(b2);
        mainPanel.add(b3);
        mainPanel.add(b4);
        mainPanel.add(b5);
        mainPanel.add(b6);
        mainPanel.add(b7);
        mainPanel.add(b_gen);

        add(mainPanel);
    }
    public String processLogs(ArrayList<String> logs) {
        String str_logs = "";
        for (int i = 0; i < logs.size(); i++)
            str_logs += logs.get(i) + '\n';
        return str_logs;
    }

    class addButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            ArrayList<String> arr_logs;
            String str_logs = "";
            getContentPane().removeAll();
            switch (ae.getActionCommand()) {
                case "Static file":
                    staticFile = true;
                    createMainPanel();
                    addMainPanel();

                    break;
                case "Dynamic file":
                    staticFile = false;
                    createMainPanel();
                    addMainPanel();
                    break;

                case "#1 Vyhladanie zaznamov pacienta":
                    createPanel1();
                    getContentPane().add(panel1);
                    break;
                case "#2 Vyhladanie hospitalizacie":
                    createPanel2();
                    getContentPane().add(panel2);
                    break;
                case "#3 Zacat hospitalizaciu":
                    createPanel3();
                    getContentPane().add(panel3);
                    break;
                case "#4 Ukoncit hospitalizaciu":
                    createPanel4();
                    getContentPane().add(panel4);
                    break;
                case "#5 Pridat pacienta":
                    createPanel5();
                    getContentPane().add(panel5);
                    break;
                case "#6 Vymazanie hospitalizacie":
                    createPanel6();
                    getContentPane().add(panel6);
                    break;
                case "#7 Vymazanie pacienta":
                    createPanel7();
                    getContentPane().add(panel7);
                    break;
                case "Generate Data":
                    createPanel_Gen();
                    getContentPane().add(panel_gen);
                    break;
                case "Main screen":
                    getContentPane().add(mainPanel);
                    break;
                case "B1_CONFIRM":
                    //arr_logs = mp.task1(rod_cislo.getText(), nemocnica.getText());
                    //str_logs = processLogs(arr_logs);
                    createPanel1();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel1);
                    break;
                case "B2_CONFIRM":
                    //arr_logs = mp.task2(nemocnica.getText(), meno.getText(), priezvisko.getText());
                    //str_logs = processLogs(arr_logs);
                    createPanel2();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel2);
                    break;

                case "B3_CONFIRM":
                    //arr_logs = mp.task3(rod_cislo.getText(), nemocnica.getText(), LocalDateTime.parse(d_hosp.getText(), formatter) , diagnoza.getText());
                    //str_logs = processLogs(arr_logs);
                    createPanel3();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel3);
                    break;
                case "B4_CONFIRM":
                    //arr_logs = mp.task4(rod_cislo.getText(), nemocnica.getText(), LocalDateTime.parse(d_k_hosp.getText(),formatter));
                    //str_logs = processLogs(arr_logs);
                    createPanel4();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel4);
                    break;
                case "B5_CONFIRM":
                    //arr_logs = mp.task5(nemocnica.getText(), LocalDateTime.parse(d_hosp_od.getText(),formatter), LocalDateTime.parse(d_hosp_do.getText(),formatter));
                    //str_logs = processLogs(arr_logs);
                    createPanel5();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel5);
                    break;
                case "B6_CONFIRM":
                    //arr_logs = mp.task6(meno.getText(), priezvisko.getText(), rod_cislo.getText(), LocalDate.parse(d_narodenia.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")), poistovna.getText());
                    //str_logs = processLogs(arr_logs);
                    createPanel6();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel6);
                    break;
                case "B7_CONFIRM":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    LocalDateTime mesiac_rok = LocalDateTime.parse("01-" + d_mesiac_rok.getText() + " 00:00", formatter);

                    //arr_logs = mp.task7(nemocnica.getText(), mesiac_rok);
                    //str_logs = processLogs(arr_logs);
                    createPanel7();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel7);
                    break;

                case "B_GEN_CONFIRM":
                    //arr_logs = mp.fillDatabase(nemocnice_num.getText(), pacienti_num.getText(), hospitalizacie_num.getText());
                    //str_logs = processLogs(arr_logs);
                    createPanel_Gen();
                    label_logs.setText(str_logs);
                    getContentPane().add(panel_gen);
                    break;
            }

            repaint();
            printAll(getGraphics());
        }
    }
}

