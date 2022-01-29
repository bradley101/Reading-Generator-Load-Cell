package com.bradley.readinggenerator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

/*
 * @author bradley
 * created by bradley - 03 June 2017
*/
public class Main extends JFrame {

	static final String DESKTOP_DIR = System.getProperty("user.home") + "/Desktop/";
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private final JSeparator separator = new JSeparator();
	private JTable table;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 486);
		setTitle("Readings Generator - shantanu.banerjee.vt@gmail.com");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Project Name");
		lblNewLabel.setBounds(422, 36, 79, 20);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(508, 36, 116, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Serial Number");
		lblNewLabel_1.setBounds(10, 70, 94, 14);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(101, 67, 116, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Select Catogery");
		lblNewLabel_2.setBounds(241, 70, 94, 14);
		contentPane.add(lblNewLabel_2);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(345, 67, 116, 20);
		comboBox.addItem("300");
		comboBox.addItem("500");
		comboBox.addItem("750");
		comboBox.addItem("1000");
		comboBox.addItem("1500");
		comboBox.addItem("2000");
		comboBox.addItem("2500");
		comboBox.addItem("3000");
		
		comboBox.addItem("30");
		comboBox.addItem("50");
		comboBox.addItem("75");
		comboBox.addItem("100");
		comboBox.addItem("150");
		comboBox.addItem("200");
		comboBox.addItem("250");
		comboBox.addItem("500");
		
		contentPane.add(comboBox);
		separator.setBounds(0, 95, 684, 8);
		contentPane.add(separator);

		table = new JTable(new DefaultTableModel());
		table.setBounds(37, 225, 587, 164);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addColumn("load");
		model.addColumn("red");
		model.addColumn("green");
		model.addColumn("blue");
		model.addColumn("average");
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		model.addRow(new Object[] { "Load", "Red Reading", "Blue Reading", "Green Reading", "Average" });
		contentPane.add(table);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(290, 413, 89, 23);
		contentPane.add(btnSave);

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selection = comboBox.getSelectedIndex();
				makeChangesToTable(selection);
			}
		});
				
		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(10, 11, 59, 14);
		contentPane.add(lblCustomer);
		
		textField_4 = new JTextField();
		textField_4.setBounds(79, 8, 116, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblPoRef = new JLabel("P.O. Ref");
		lblPoRef.setBounds(237, 11, 46, 14);
		contentPane.add(lblPoRef);
		
		textField_5 = new JTextField();
		textField_5.setBounds(302, 8, 116, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblInstrument = new JLabel("Instrument");
		lblInstrument.setBounds(454, 11, 73, 14);
		contentPane.add(lblInstrument);
		
		textField_6 = new JTextField();
		textField_6.setBounds(537, 8, 116, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblRange = new JLabel("Range");
		lblRange.setBounds(10, 39, 46, 14);
		contentPane.add(lblRange);
		
		textField_7 = new JTextField();
		textField_7.setBounds(66, 36, 116, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(219, 39, 46, 14);
		contentPane.add(lblDate);
		
		textField_8 = new JTextField();
		textField_8.setBounds(275, 36, 116, 20);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		textField_4.setText("");
		textField_5.setText("");
		textField_6.setText("");
		textField_7.setText("");
		textField_8.setText("");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(566, 67, 58, 20);
		comboBox_1.addItem("kN");
		comboBox_1.addItem("Tonne");
		
		contentPane.add(comboBox_1);
		
		JLabel lblSelectUnit = new JLabel("Select Unit");
		lblSelectUnit.setBounds(481, 70, 75, 14);
		contentPane.add(lblSelectUnit);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Red", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(37, 114, 180, 96);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField_9 = new JTextField();
		textField_9.setBounds(84, 21, 86, 20);
		panel.add(textField_9);
		textField_9.setColumns(10);
		
		JLabel label = new JLabel("Zero Reading");
		label.setBounds(10, 21, 72, 20);
		panel.add(label);
		
		JLabel lblSecdReading = new JLabel("Secd Reading");
		lblSecdReading.setBounds(10, 52, 72, 20);
		panel.add(lblSecdReading);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(84, 52, 86, 20);
		panel.add(textField_10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Green", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(241, 114, 180, 96);
		contentPane.add(panel_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(84, 21, 86, 20);
		panel_1.add(textField_11);
		
		JLabel label_1 = new JLabel("Zero Reading");
		label_1.setBounds(10, 21, 72, 20);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("Secd Reading");
		label_2.setBounds(10, 52, 72, 20);
		panel_1.add(label_2);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(84, 52, 86, 20);
		panel_1.add(textField_12);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "White", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(444, 114, 180, 96);
		contentPane.add(panel_2);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(84, 21, 86, 20);
		panel_2.add(textField_13);
		
		JLabel label_3 = new JLabel("Zero Reading");
		label_3.setBounds(10, 21, 72, 20);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("Secd Reading");
		label_4.setBounds(10, 52, 72, 20);
		panel_2.add(label_4);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(84, 52, 86, 20);
		panel_2.add(textField_14);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textField_4, textField_5, textField_6, textField_7, textField_8, textField, textField_1, comboBox, comboBox_1, textField_9, textField_10, textField_11, textField_12, textField_13, textField_14, btnSave, lblNewLabel, lblNewLabel_1, lblNewLabel_2, separator, table, lblCustomer, lblPoRef, lblInstrument, lblRange, lblDate, lblSelectUnit, panel, label, lblSecdReading, panel_1, label_1, label_2, panel_2, label_3, label_4}));

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textField_6.getText() == null || textField_6.getText().equals("") || textField.getText() == null || textField.getText().equals("") || textField_1.getText() == null
						|| textField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Project Name, Serial Number or Instrument Name",
							"Readings Generator - shantanu.banerjee.vt@gmail.com", JOptionPane.ERROR_MESSAGE);
					return;
				}

				File dir = new File(DESKTOP_DIR + textField.getText());
				File cDir = new File(dir, textField_6.getText() + ((String) comboBox.getSelectedItem()) + (String) comboBox_1.getSelectedItem());
				cDir.mkdirs();
				File dFile = new File(cDir, textField_1.getText() + ".pdf");
				destFile = dFile.getAbsolutePath();
				if (dFile.exists()) {
					int opt = JOptionPane.showConfirmDialog(null,
							"File already exits. \nDo you want to overwrite the existing file?",
							"Readings Generator - shantanu.banerjee.vt@gmail.com", JOptionPane.YES_NO_OPTION);
					if (opt == JOptionPane.YES_OPTION)
						;
					else
						return;
				}
				
				unit = (String) comboBox_1.getSelectedItem();
				saveToTemplate();
				JOptionPane.showMessageDialog(null, "Saved successfully!",
						"Readings Generator - shantanu.banerjee.vt@gmail.com", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		FocusAdapter fa = new FocusAdapter() {
			
			public void focusGained(FocusEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						((JTextField)evt.getSource()).selectAll();
					}
				});
			}
		};
		textField_9.addFocusListener(fa);
		textField_10.addFocusListener(fa);
		textField_11.addFocusListener(fa);
		textField_12.addFocusListener(fa);
		textField_13.addFocusListener(fa);
		textField_14.addFocusListener(fa);
		
		ActionListener textFieldActionListner = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] red = {
					textField_9.getText(),
					textField_10.getText()
				};
				String[] blue = {
					textField_11.getText(),
					textField_12.getText()
				};
				String[] green= {
					textField_13.getText(),
					textField_14.getText()
				};
				insertIntoTableCol1(new String[][] {red,  blue, green});
			}
		};
		
		textField_9.addActionListener(textFieldActionListner);
		textField_10.addActionListener(textFieldActionListner);
		textField_11.addActionListener(textFieldActionListner);
		textField_12.addActionListener(textFieldActionListner);
		textField_13.addActionListener(textFieldActionListner);
		textField_14.addActionListener(textFieldActionListner);
		
		makeChangesToTable(0);
	}

	String unit;
	
	protected void insertIntoTableCol1(String[][] data) {
		// TODO Auto-generated method stub
		for (int i = 0; i < data.length; ++i) {
			if (data[i][0].equals("") || data[i][1].equals(""))
				return;
		}
		
		float[] red = {
				Float.parseFloat(data[0][0]),
				Float.parseFloat(data[0][1])
		};
		float[] blue = {
				Float.parseFloat(data[1][0]),
				Float.parseFloat(data[1][1])
		};
		float[] green = {
				Float.parseFloat(data[2][0]),
				Float.parseFloat(data[2][1])
		};
		
		Reading redReading = new Reading(red[0], red[1], num);
		Reading blueReading = new Reading(blue[0], blue[1], num);
		Reading greenReading = new Reading(green[0], green[1], num);
		
		List<Reading> readings = Arrays.asList(new Reading[] {redReading, blueReading, greenReading});
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for (int i = 0; i < readings.size(); ++i) {
			float[] r = readings.get(i).getReadings();
			for (int j = 0; j < r.length; ++j) {
				model.setValueAt(String.format("%.0f", r[j]), j + 1, i + 1);
			}
		}
		
		float[] avgs = Reading.getAverage(readings);
		for (int i = 0; i < avgs.length; ++i) {
			model.setValueAt(String.format("%.0f", avgs[i]), i + 1, 4);
		}

	}

	float maxLoad = 0, difference = 0;
	int num = 0;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	
	private String destFile;

	protected void makeChangesToTable(int selection) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		maxLoad = Float.valueOf(comboBox.getSelectedItem().toString());
		difference = maxLoad / 5;
		num = 5;
		
		model.setRowCount(num + 2);

		for (int i = 1; i < model.getRowCount(); i++) {
			model.setValueAt("", i, 0);
			model.setValueAt("", i, 1);
			model.setValueAt("", i, 2);
		}

		for (int i = 0; i <= num; i++) {
			model.setValueAt(String.format("%.1f", i * difference), i + 1, 0);
		}

	}
	
	double[][] data;
	double x[], y[], abc[], gf, lpress[], ppress[], lpresserr[], ppresserr[];
	String customer, poref, instrument, range, date, sno;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	
	@SuppressWarnings({ "unchecked" })
	void saveToTemplate() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector<Vector> v = model.getDataVector();
		customer = textField_4.getText();
		poref = textField_5.getText();
		instrument = textField_6.getText();
		range = textField_7.getText();
		date = textField_8.getText();
		sno = textField_1.getText();
		
		data = new double [v.size() - 1][v.get(0).size()];
		
		for (int i = 1 ; i < v.size() ; i++) {
			Vector<Object> vv = v.get(i);
			for (int j = 0 ; j < vv.size() ; j++) {
				data[i - 1][j] = Double.parseDouble((String) vv.get(j));
			}
		}
		
		y = new double[data.length];
		x = new double[data.length];
		for (int i = 0 ; i < data.length ; i++) {
			y[i] = data[i][0];
			x[i] = data[i][data[0].length - 1];
		}
		
		gf = y[y.length - 1] / (x[0] - x[x.length - 1]);
		
		WeightedObservedPoints pts = new WeightedObservedPoints();
		for (int i = 0 ; i < data.length ; i++) {
			pts.add(x[i], y[i]);
		}
		PolynomialCurveFitter fitter =PolynomialCurveFitter.create(2);
		abc = fitter.fit(pts.toList());
		
		lpress = new double[data.length];
		ppress = new double[data.length];
		lpresserr = new double[data.length];
		ppresserr = new double[data.length];
		
		for (int i = 0 ; i < data.length ; i++) {
			lpress[i] = gf * (x[0] - x[i]);
			ppress[i] = abc[2] * (x[i] * x[i]) + abc[1] * (x[i]) + abc[0];
			lpresserr[i] = Math.abs(y[i] - lpress[i]) / y[y.length - 1] * 100;
			ppresserr[i] = Math.abs(y[i] - ppress[i]) / y[y.length - 1] * 100;
		}
		
		savePDF();
		
	}

	private void savePDF() {
		// TODO Auto-generated method stub
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(destFile));
			
			String[][] t1L = { 
					{"Customer", customer},
					{"P.O. Ref", poref},
					{"Instrument", instrument},
					{"Date", date},
					{"Range", range},
					{"R.Temp", "23� C"},
					{"Mfg. Sl. No", sno},
					{"Atm. Press", "1004 mb"}
			};
			doc.setMargins(70, 70, 170, 50);
			doc.open();
			doc.setMargins(0, 50, 50, 10);
			PdfPTable t1 = new PdfPTable(new float[] {4, 7, 4, 7});
			t1.setWidthPercentage(100);
			PdfPCell p = new PdfPCell(), q = new PdfPCell();
			Phrase pp = new Phrase(), qp = new Phrase();
			
			for (int i = 0 ; i < t1L.length ; i++) {
				pp = new Phrase(t1L[i][0]); qp = new Phrase(t1L[i][1]);
				p.setPhrase(pp);
				q.setPhrase(qp);
				
				if (i < 2) {
					if (i == 0) {
						p.setBorder(Rectangle.LEFT|Rectangle.TOP);
						q.setBorder(Rectangle.TOP);
					} else {
						p.setBorder(Rectangle.TOP);
						q.setBorder(Rectangle.TOP|Rectangle.RIGHT);
					}
				} else if (i < 4) {
					if (i == 2) {
						p.setBorder(Rectangle.LEFT);
						q.setBorder(0);
					} else {
						p.setBorder(0);
						q.setBorder(Rectangle.RIGHT);
					}
				} else if (i < 6) {
					if (i == 4) {
						p.setBorder(Rectangle.LEFT);
						q.setBorder(0);
					} else {
						p.setBorder(0);
						q.setBorder(Rectangle.RIGHT);
					} 
				} else {
					if (i == 6) {
						p.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
						q.setBorder(Rectangle.BOTTOM);
					} else {
						p.setBorder(Rectangle.BOTTOM);
						q.setBorder(Rectangle.RIGHT|Rectangle.BOTTOM);
					}
				}
				
				t1.addCell(p);
				t1.addCell(q);
			}
			
			String[] t2L = {
					"Force",
					"O/P DIGIT (f�/1000)",
					"End Point",
					"End Point",
			};
			
			PdfPTable t2 = new PdfPTable(new float[] {1, 4, 1, 1});
			t2.setWidthPercentage(100);
			p = new PdfPCell();
			
			for (int i = 0 ; i < t2L.length ; i++) {
				pp = new Phrase(t2L[i]);
				p.setPhrase(pp);
				t2.addCell(p);
			}
			
			String[] t3L = {
					unit,
					"Gauge 1",
					"Gauge 2",
					"Gauge 3",
					"Avg",
					"Acc. " + unit,
					"NL.%F.S.",
			};
			PdfPTable t3 = new PdfPTable(new float[] {1, 1, 1, 1, 1, 1, 1});
			t3.setWidthPercentage(100);
			p = new PdfPCell();
			
			for (int i = 0 ; i < t3L.length ; i++) {
				pp = new Phrase(t3L[i]);
				p.setPhrase(pp);
				t3.addCell(p);
			}
			double max1 = Double.MIN_VALUE, max2 = Double.MIN_VALUE;
			for (int i = 0 ; i < y.length ; i++) {
				p = new PdfPCell();
				p.setBorder(Rectangle.LEFT);
				p.setPhrase(new Phrase(String.format("%.1f", y[i])));
				t3.addCell(p);
				p.setBorder(0);
				p.setPhrase(new Phrase(String.format("%.0f", data[i][1])));
				t3.addCell(p);
				p.setPhrase(new Phrase(String.format("%.0f", data[i][2])));
				t3.addCell(p);
				p.setPhrase(new Phrase(String.format("%.0f", data[i][3])));
				t3.addCell(p);
				p.setPhrase(new Phrase(String.format("%.0f", Math.floor(x[i]))));
				p.setGrayFill(0.8f);
				t3.addCell(p);
				p.setGrayFill(1.0f);
				p.setPhrase(new Phrase(String.format("%.3f", lpress[i])));
				t3.addCell(p);
//				p.setPhrase(new Phrase(String.format("%.3f", ppress[i])));
//				t3.addCell(p);
				p.setBorder(Rectangle.RIGHT);
				p.setPhrase(new Phrase(String.format("%.2f", lpresserr[i])));
				t3.addCell(p);
//				p.setBorder(Rectangle.RIGHT);
//				p.setPhrase(new Phrase(String.format("%.2f", ppresserr[i])));
//				t3.addCell(p);
				
				max1 = Math.max(max1, lpresserr[i]);
				max2 = Math.max(max2, ppresserr[i]);
				
			}
			
			p = new PdfPCell();
			p.setBorder(Rectangle.LEFT|Rectangle.TOP);
			t3.addCell(p); p.setBorder(Rectangle.TOP); t3.addCell(p); t3.addCell(p); t3.addCell(p);
			p = new PdfPCell();
			p.setPhrase(new Phrase("NL%F.S."));
			t3.addCell(p);
			p.setPhrase(new Phrase(String.format("%.2f", max1)));
			t3.addCell(p);
//			p.setPhrase(new Phrase(String.format("%.2f", max2)));
//			t3.addCell(p);
//			p.setPhrase(new Phrase(String.format("%.2f", max1)));
//			t3.addCell(p);
//			p.setPhrase(new Phrase(String.format("%.2f", max2)));
//			t3.addCell(p);
			
			DecimalFormat format = new DecimalFormat("0.####E0");
			PdfPTable t4 = new PdfPTable(2);
			t4.setWidthPercentage(100);
			p = new PdfPCell(new Phrase("LINEAR "));
			p.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
			p.setGrayFill(0.8f);
			t4.addCell(p);
			p = new PdfPCell(new Phrase("(G) = " + String.valueOf(format.format(gf)) + " " + unit + "/DIGIT"));
			p.setBorder(Rectangle.TOP|Rectangle.RIGHT|Rectangle.BOTTOM);
			p.setGrayFill(0.8f);
			t4.addCell(p);
			p = new PdfPCell(new Phrase("THERMAL "));
			p.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
			t4.addCell(p);
			p = new PdfPCell(new Phrase("(K) = " + String.valueOf(format.format(0.000126)) + " " + unit + "� C"));
			p.setBorder(Rectangle.TOP|Rectangle.RIGHT|Rectangle.BOTTOM);
			t4.addCell(p);
			
//			PdfPTable t5 = new PdfPTable(4);
//			t5.setWidthPercentage(100);
//			t5.addCell(new PdfPCell(new Phrase("Polynomial Const.")));
//			
//			String sa = String.valueOf(format.format(abc[2]));
//			p = new PdfPCell(new Phrase("A = " + sa));
//			t5.addCell(p);
//			sa = String.valueOf(format.format(abc[1]));
//			p = new PdfPCell(new Phrase("B = " + sa));
//			t5.addCell(p);
//			sa = String.valueOf(format.format(abc[0]));
//			p = new PdfPCell(new Phrase("C = " + sa));
//			t5.addCell(p);
			
			PdfPTable t6 = new PdfPTable(1);
			t6.setWidthPercentage(100);
			p = new PdfPCell();
			p.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
			
			String[] t4L = {
					"",
					"To Calculate Load \"L\" use the following equation",
					"Load L = G(R0 - R1)",
					"R1= Current reading in digit during observation.",
					"",
					"Note: The user is advised to establish the zero reading at known temp. at site",
					"",
					"Wire\tCOLOR CODE",
					"BLACK\tCOMMON",
					"RED\tGAUGE 1",
					"GREEN\tGAUGE 2",
					"WHITE\tGAUGE 3",
					"Current reading during observation"
			};
			
			for (int i = 0 ; i < t4L.length ; i++) {
				if (i != t4L.length - 1) {
					p.setPhrase(new Phrase(t4L[i]));
					if (i != 0) p.setBorder(Rectangle.LEFT|Rectangle.RIGHT);
				} else {
					p.setBorder(Rectangle.LEFT|Rectangle.BOTTOM|Rectangle.RIGHT);
					p.setPhrase(new Phrase(t4L[i]));
				}
				
				p.setGrayFill(i == 5 ? 0.8f : 1f);
				t6.addCell(p);
			}
			
			PdfPTable t7 = new PdfPTable(3);
			t7.setWidthPercentage(100);
			p = new PdfPCell(new Phrase("GAUGE 1:\t\t" + data[0][1]));
			p.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT);
			t7.addCell(p);
			p = new PdfPCell(new Phrase("GAUGE 2:\t\t" + data[0][2]));
			p.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
			t7.addCell(p);
			p = new PdfPCell(new Phrase("GAUGE 3:\t\t" + data[0][3]));
			p.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
			t7.addCell(p);
			
			doc.add(t1);
			doc.add(t2);
			doc.add(t3);
			doc.add(t4);
//			doc.add(t5);
			doc.add(t6);
			doc.add(t7);
			doc.close();
			
			
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
