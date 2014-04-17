package view;

import helper.Statics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Analysis;

public class OrderDialog extends JDialog
{
	private static final long	serialVersionUID	= 1L;
	private static final String[] ORDERING = {"ASC", "DESC"};
	
	private Analysis analysis;
	
	private JButton orderBtn, cancelBtn;
	private JPanel btnPanel, orderPanel;
	private JComboBox<String> orderAttribute, ordering;
	private ActionListener orderBtnListener, cancelBtnListener;
	
	private int width = 400, height = 200;
	
	public OrderDialog(Analysis analysis)
	{
		this.analysis = analysis;
		createListeners();
		initialize();
		createElements();
		attachElements();
	}
	
	private void initialize()
	{
		this.setSize(width, height);
		this.setLayout(new BorderLayout());
		this.setLocation(Statics.getCenterLocation(this.getWidth(), this.getHeight()));
	}
	
	private void createElements()
	{
		orderPanel = new JPanel();
		orderPanel.setLayout(new FlowLayout());
		orderAttribute = new JComboBox<String>(Analysis.ATTRIBUTES);
		ordering = new JComboBox<String>(ORDERING);
		btnPanel = new JPanel();
		orderBtn = new JButton("Order");	
		orderBtn.addActionListener(orderBtnListener);
		cancelBtn = new JButton("Cancel");	
		cancelBtn.addActionListener(cancelBtnListener);
	}

	private void createListeners()
	{		
		orderBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				analysis.sortData((String)orderAttribute.getSelectedItem(), ordering.getSelectedItem().equals(ORDERING[0]));
				setVisible(false);
			}		
		};
		
		cancelBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}		
		};
	}
	
	private void attachElements()
	{
		orderPanel.add(orderAttribute);
		orderPanel.add(ordering);
		this.add(orderPanel);
		
		btnPanel.add(orderBtn);
		btnPanel.add(cancelBtn);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}
