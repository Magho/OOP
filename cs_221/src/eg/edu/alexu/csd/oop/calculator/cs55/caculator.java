package eg.edu.alexu.csd.oop.calculator.cs55;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class caculator {
	eg.edu.alexu.csd.oop.calculator.Calculator calc = new CalculatorImpl();
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					caculator window = new caculator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public caculator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			calc.load();
		} catch (Exception e) {
		}
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 576, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(12, 13, 533, 63);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 4;
				textField.setText(formula);
			}
		});
		button_4.setForeground(Color.WHITE);
		button_4.setBackground(Color.RED);
		button_4.setBounds(12, 244, 97, 63);
		frame.getContentPane().add(button_4);

		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 1;
				textField.setText(formula);
			}
		});

		button_1.setForeground(Color.WHITE);
		button_1.setBackground(Color.RED);
		button_1.setBounds(12, 166, 97, 63);
		frame.getContentPane().add(button_1);

		JButton button_7 = new JButton("7");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 7;
				textField.setText(formula);
			}
		});
		button_7.setForeground(Color.WHITE);
		button_7.setBackground(Color.RED);
		button_7.setBounds(12, 320, 97, 63);
		frame.getContentPane().add(button_7);

		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 2;
				textField.setText(formula);
			}
		});
		button_2.setBackground(Color.RED);
		button_2.setForeground(Color.WHITE);
		button_2.setBounds(121, 166, 97, 63);
		frame.getContentPane().add(button_2);

		JButton button_5 = new JButton("5");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 5;
				textField.setText(formula);
			}
		});
		button_5.setForeground(Color.WHITE);
		button_5.setBackground(Color.RED);
		button_5.setBounds(121, 244, 97, 63);
		frame.getContentPane().add(button_5);

		JButton button_8 = new JButton("8");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 8;
				textField.setText(formula);
			}
		});
		button_8.setForeground(Color.WHITE);
		button_8.setBackground(Color.RED);
		button_8.setBounds(121, 320, 97, 63);
		frame.getContentPane().add(button_8);

		JButton button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 3;
				textField.setText(formula);
			}
		});
		button_3.setForeground(Color.WHITE);
		button_3.setBackground(Color.RED);
		button_3.setBounds(230, 166, 97, 63);
		frame.getContentPane().add(button_3);

		JButton button_6 = new JButton("6");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 6;
				textField.setText(formula);
			}
		});
		button_6.setForeground(Color.WHITE);
		button_6.setBackground(Color.RED);
		button_6.setBounds(230, 244, 97, 63);
		frame.getContentPane().add(button_6);

		JButton button_9 = new JButton("9");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 9;
				textField.setText(formula);
			}
		});
		button_9.setForeground(Color.WHITE);
		button_9.setBackground(Color.RED);
		button_9.setBounds(230, 320, 97, 63);
		frame.getContentPane().add(button_9);

		JButton btnC = new JButton("C");
		btnC.setForeground(Color.WHITE);
		btnC.setBackground(Color.RED);
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		btnC.setBounds(12, 90, 206, 63);
		frame.getContentPane().add(btnC);

		JButton button_0 = new JButton("0");
		button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + 0;
				textField.setText(formula);
			}
		});
		button_0.setForeground(Color.WHITE);
		button_0.setBackground(Color.RED);
		button_0.setBounds(12, 396, 206, 63);
		frame.getContentPane().add(button_0);

		JButton button_11 = new JButton("+");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + '+';
				textField.setText(formula);
			}
		});
		button_11.setForeground(Color.WHITE);
		button_11.setBackground(Color.RED);
		button_11.setBounds(339, 89, 97, 63);
		frame.getContentPane().add(button_11);

		JButton button_12 = new JButton("-");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + '-';
				textField.setText(formula);
			}
		});
		button_12.setForeground(Color.WHITE);
		button_12.setBackground(Color.RED);
		button_12.setBounds(339, 165, 97, 63);
		frame.getContentPane().add(button_12);

		JButton button_13 = new JButton("*");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + '*';
				textField.setText(formula);
			}
		});
		button_13.setForeground(Color.WHITE);
		button_13.setBackground(Color.RED);
		button_13.setBounds(339, 243, 97, 63);
		frame.getContentPane().add(button_13);

		JButton button_14 = new JButton("/");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = textField.getText() + '/';
				textField.setText(formula);
			}
		});
		button_14.setForeground(Color.WHITE);
		button_14.setBackground(Color.RED);
		button_14.setBounds(339, 319, 97, 63);
		frame.getContentPane().add(button_14);

		JButton button_15 = new JButton("=");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(calc.getResult());
			}
		});
		button_15.setForeground(Color.WHITE);
		button_15.setBackground(Color.RED);
		button_15.setBounds(339, 396, 97, 63);
		frame.getContentPane().add(button_15);

		JButton btncurrent = new JButton("current");
		btncurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(calc.current());
			}
		});
		btncurrent.setForeground(Color.WHITE);
		btncurrent.setBackground(Color.RED);
		btncurrent.setBounds(448, 89, 97, 63);
		frame.getContentPane().add(btncurrent);

		JButton btnNext = new JButton("next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = calc.next();
				if (formula == null) {
				} else {
					textField.setText(formula);
				}
			}
		});
		btnNext.setForeground(Color.WHITE);
		btnNext.setBackground(Color.RED);
		btnNext.setBounds(448, 165, 97, 63);
		frame.getContentPane().add(btnNext);

		JButton btnPrev = new JButton("previous");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formula = calc.prev();
				if (formula == null) {
				} else {
					textField.setText(formula);
				}
			}
		});
		btnPrev.setForeground(Color.WHITE);
		btnPrev.setBackground(Color.RED);
		btnPrev.setBounds(448, 243, 97, 63);
		frame.getContentPane().add(btnPrev);

		JButton btnSave = new JButton("save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.save();
			}
		});
		btnSave.setForeground(Color.WHITE);
		btnSave.setBackground(Color.RED);
		btnSave.setBounds(448, 319, 97, 63);
		frame.getContentPane().add(btnSave);

		JButton btnLoad = new JButton("load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.load();
			}
		});
		btnLoad.setForeground(Color.WHITE);
		btnLoad.setBackground(Color.RED);
		btnLoad.setBounds(448, 396, 97, 63);
		frame.getContentPane().add(btnLoad);

		JButton button = new JButton(".");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String formula = textField.getText() + '.';
				textField.setText(formula);
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(Color.RED);
		button.setBounds(230, 396, 97, 63);
		frame.getContentPane().add(button);
	}
}
