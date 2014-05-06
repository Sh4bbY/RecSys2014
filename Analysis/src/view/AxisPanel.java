package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;

public class AxisPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	
	private JRadioButton[] xAttributes;
	private JCheckBox[][] yAttributes;
	private JPanel[] attributePanel;
	private JScrollPane scrollPane;
	private JPanel attrPanelsPanel;
	private JLabel xTitle,yTitle;
	private ButtonGroup bGroup;
	private ActionListener radioBtnListener,checkBoxListener;
	private ConfigDialog configDialog;
	
	public AxisPanel(ConfigDialog configDialog)
	{
		this.configDialog = configDialog;
		
		initialize();
		createListeners();
		createElements();
		attachElements();
	}
	
	public void selectXAxis(XAxis xAxis)
	{
		XAxis[] values = XAxis.values();
		
		for(int i=0;i<values.length;i++)
		{
			if(xAxis.equals(values[i]))
			{
				xAttributes[i].doClick();
				return;
			}
		}	
	}
	
	private void initialize()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Axis"));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
		setPreferredSize(new Dimension(150, 300));
	}
	
	private void createListeners()
	{
		radioBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String btnText = ((JRadioButton)e.getSource()).getText();
				XAxis selection = null;
				
				for(XAxis tmp : XAxis.values())
				{
					if(btnText.equals(tmp.toString()))
					{
						selection = tmp;
						break;
					}
				}				
				
				switch(selection)
				{
					case Rating:	showAttributePanel(0); break;
					case User:		showAttributePanel(1); break;
					case Movie:		showAttributePanel(2); break;
				}
				
				configDialog.xAxisUpdate(selection);
			}
		};
		
		checkBoxListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int xAxisIdx = 0;
				
				for(int i=0;i<xAttributes.length;i++)
				{
					if(xAttributes[i].isSelected())
					{
						xAxisIdx = i;
					}
				}
				
				JCheckBox[] checkBoxes = yAttributes[xAxisIdx];
				int selectedCount = 0;
				
				for(JCheckBox checkBox : checkBoxes)
				{
					if(checkBox.isSelected())
					{
						selectedCount++;
					}
				}
				
				if(selectedCount == 0)
				{
					((JCheckBox)e.getSource()).setSelected(true);
				}
			}
		};
	}
	
	private void showAttributePanel(int i)
	{
		for(JPanel panel : attributePanel)
		{
			panel.setVisible(false);
		}
		attributePanel[i].setVisible(true);
	}
	
	private void createElements()
	{
		xTitle = new JLabel("X-Axis");
		yTitle = new JLabel("Y-Axis");
		
		bGroup = new ButtonGroup();
		
		XAxis[] xAxis = XAxis.values(); 
		Attribute[] attrArr = null;

		xAttributes = new JRadioButton[xAxis.length];
		attributePanel = new JPanel[xAxis.length];

		yAttributes = new JCheckBox[xAxis.length][];
		
		for(int i=0; i<xAxis.length; i++)
		{
			attributePanel[i] = new JPanel();
			attributePanel[i].setVisible(false);
			attributePanel[i].setLayout(new BoxLayout(attributePanel[i], BoxLayout.Y_AXIS));
			
			xAttributes[i] = new JRadioButton();
			xAttributes[i].addActionListener(radioBtnListener);
			bGroup.add(xAttributes[i]);
			
			switch(i)
			{
				case 0: attrArr = RatingAttr.values(); 	break;
				case 1: attrArr = UserAttr.values(); 	break;
				case 2: attrArr = MovieAttr.values(); 	break;
			}
			
			xAttributes[i].setText(xAxis[i].toString());
			
			

			yAttributes[i] = new JCheckBox[attrArr.length];
			
			for(int o=0; o<attrArr.length; o++)
			{
				yAttributes[i][o] = new JCheckBox(attrArr[o].toString());
				yAttributes[i][o].addActionListener(checkBoxListener);
				attributePanel[i].add(yAttributes[i][o]);

				if(o==0)
				{
					yAttributes[i][o].setSelected(true);
					
				}
			}
		}
		
		attrPanelsPanel = new JPanel();

		
		for(JPanel attrPanel : attributePanel)
		{
			attrPanelsPanel.add(attrPanel);
		}

		scrollPane = new JScrollPane(attrPanelsPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	public XAxis getXAxis()
	{
		for(int i=0; i<xAttributes.length; i++)
		{
			if(xAttributes[i].isSelected())
			{
				return XAxis.values()[i];
			}
		}
		
		return null;
	}
	

	public int[][] getSelectedAttributes()
	{
		int[][] selectedAttributes = new int[yAttributes.length][];
		
		for(int i=0, count=0; i<yAttributes.length; i++, count=0)
		{
			selectedAttributes[i] = new int[yAttributes[i].length];
			
			for(int o=0; o<yAttributes[i].length; o++)
			{
				if(yAttributes[i][o].isSelected())
				{
					selectedAttributes[i][count] = o;
					count++;
				}
			}
			
			selectedAttributes[i] = Arrays.copyOf(selectedAttributes[i], count);
		}
		
		return selectedAttributes;
	}
	
	
	private void attachElements()
	{
		add(xTitle);
		
		for(JRadioButton radioBtn : xAttributes)
		{
			add(radioBtn);
		}
		
		add(yTitle);
		add(scrollPane);		
	}
}
