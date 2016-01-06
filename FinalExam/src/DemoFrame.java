
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DemoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private DemoModel model;
	
	
	public DemoFrame(DemoModel model) throws ModelException  {
		this.model = model;
		List<Property> properties = model.getProperties();
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		JPanel modelGrid = new JPanel();
		modelGrid.setLayout(new GridLayout(properties.size(), 2));
		
		
		for(Property prop : properties) {
			modelGrid.add(new JLabel(prop.getName()));
			JTextField field = new JTextField();
			Object value = prop.getValue();
			field.setText(value != null ? value.toString() : "null");
			modelGrid.add(field);
		}
		contentPane.add(modelGrid, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton button = new JButton("SAVE");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Not implemented");
			}
		});
		
		buttons.add(button);
		contentPane.add(buttons, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
	}
	
	
	public static void main(String[] args) throws ModelException {
		DemoModel model = new DemoModel("Property");
		
		DemoFrame frame = new DemoFrame(model);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
