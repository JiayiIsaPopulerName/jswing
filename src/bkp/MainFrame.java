package bkp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * MainFrame
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField vTxt;
	private JTextField uTxt;
	private JTextField alphaTxt;
	private JTextField thetaTxt;
	private JTextField deltaTxt;
	private JTextField nTxt;
	private JTable table;
	private JTable table_1;
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("formula");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 870);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MainFrame frame = this;

		vTxt = new JTextField();
		vTxt.setText("0.3");
		vTxt.setBounds(53, 128, 86, 24);
		contentPane.add(vTxt);
		vTxt.setColumns(10);

		uTxt = new JTextField();
		uTxt.setText("0.5");
		uTxt.setColumns(10);
		uTxt.setBounds(140, 128, 86, 24);
		contentPane.add(uTxt);

		alphaTxt = new JTextField();
		alphaTxt.setText("1");
		alphaTxt.setColumns(10);
		alphaTxt.setBounds(228, 128, 86, 24);
		contentPane.add(alphaTxt);

		thetaTxt = new JTextField();
		thetaTxt.setText("1.5");
		thetaTxt.setColumns(10);
		thetaTxt.setBounds(318, 128, 86, 24);
		contentPane.add(thetaTxt);

		deltaTxt = new JTextField();
		deltaTxt.setEditable(false);
		deltaTxt.setColumns(10);
		deltaTxt.setBounds(407, 128, 86, 24);
		contentPane.add(deltaTxt);

		nTxt = new JTextField();
		nTxt.setText("10000");
		nTxt.setColumns(10);
		nTxt.setBounds(497, 128, 86, 24);
		contentPane.add(nTxt);

		JLabel lblV = new JLabel("v");
		lblV.setBounds(84, 97, 27, 18);
		contentPane.add(lblV);

		JLabel lblU = new JLabel("u");
		lblU.setBounds(174, 97, 27, 18);
		contentPane.add(lblU);

		JLabel lblV_2 = new JLabel("α");
		lblV_2.setBounds(270, 97, 27, 18);
		contentPane.add(lblV_2);

		JLabel lblV_3 = new JLabel("θ");
		lblV_3.setBounds(359, 97, 27, 18);
		contentPane.add(lblV_3);

		JLabel lblV_4 = new JLabel("δ");
		lblV_4.setBounds(449, 97, 27, 18);
		contentPane.add(lblV_4);

		JLabel lblN = new JLabel("n");
		lblN.setBounds(537, 97, 27, 18);
		contentPane.add(lblN);

		table = new JTable();
		model1 = new DefaultTableModel(new Object[][] {}, new String[] { "v^_1(x)", "u^_1(x)", "δ^_1(x)" ,"Δ_1"});
		table.setModel(model1);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(53, 246, 530, 172);
		contentPane.add(scrollPane);

		table.setBounds(53, 273, 220, 142);

		table_1 = new JTable();
		model2 = new DefaultTableModel(new Object[][] {}, new String[] { "v^_2(x)", "u^_2(x)", "δ^_2(x)", "Δ_2" });//"v", "u", "theta", "delta"
		table_1.setModel(model2);
		table_1.setBounds(53, 540, 312, 172);

		JButton btnGenerator = new JButton("Generate");
		btnGenerator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double v = 0;
				double u = 0;
				double alpha = 0;
				double theta = 0;
				double delta = 0;
				int n = 0;
				
				DecimalFormat format = new DecimalFormat("0.000000");
				
				try {
					v = Double.valueOf(vTxt.getText());
					u = Double.valueOf(uTxt.getText());
					alpha = Double.valueOf(alphaTxt.getText());
					theta = Double.valueOf(thetaTxt.getText());
					delta = alpha / theta;
					deltaTxt.setText(format.format(delta) + "");
					n = Integer.valueOf(nTxt.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Please enter the correct parameters");
					return;
				}

				// Calculate the result according to the formula
				Map<String, Double> map = FormulaUtil.generator(v, u, alpha, theta, delta, n);

				

				model1.setRowCount(0);
				model1.insertRow(table.getRowCount(),
						new Object[] { format.format(map.get("v1")), format.format(map.get("u1")),  format.format(map.get("sigma1")),format.format(map.get("delta1"))});

				model2.setRowCount(0);
				model2.insertRow(table_1.getRowCount(),
						new Object[] { format.format(map.get("v2")), format.format(map.get("u2")), format.format(map.get("sigma2")) ,format.format(map.get("delta2"))});
			}
		});
		btnGenerator.setBounds(53, 191, 113, 27);
		contentPane.add(btnGenerator);
		
		scrollPane_1 = new JScrollPane(table_1);
		scrollPane_1.setBounds(53, 506, 530, 166);
		contentPane.add(scrollPane_1);
	}
}
