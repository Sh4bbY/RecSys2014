package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JLabel xTitle,yTitle;
	private ButtonGroup bGroup;
	private ActionListener radioBtnListener;
	private ConfigDialog configDialog;
	
	public AxisPanel(ConfigDialog configDialog)
	{
		this.configDialog = configDialog;
		
		initialize();
		createListeners();
		createElements();
		attachElements();
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

		xAttributes = new JRadioButton[xAxis.length];
		attributePanel = new JPanel[xAxis.length];
		Attribute[] attrArr = null;

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
				attributePanel[i].add(yAttributes[i][o]);
			}
		}
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
	
	public ArrayList<RatingAttr> getRatingAttributes()
	{
		ArrayList<RatingAttr> attributes = new ArrayList<RatingAttr>();

		for(int o=0; o<yAttributes[0].length; o++)
		{
			if(yAttributes[0][o].isSelected())
			{
				attributes.add(RatingAttr.values()[o]);
			}
		}
		
		return attributes;
	}
	
	public ArrayList<UserAttr> getUserAttributes()
	{
		ArrayList<UserAttr> attributes = new ArrayList<UserAttr>();

		for(int o=0; o<yAttributes[1].length; o++)
		{
			if(yAttributes[1][o].isSelected())
			{
				attributes.add(UserAttr.values()[o]);
			}
		}
		
		return attributes;
	}
	
	public ArrayList<MovieAttr> getMovieAttributes()
	{
		ArrayList<MovieAttr> attributes = new ArrayList<MovieAttr>();

		for(int o=0; o<yAttributes[2].length; o++)
		{
			if(yAttributes[2][o].isSelected())
			{
				attributes.add(MovieAttr.values()[o]);
			}
		}
		
		return attributes;
	}
	
	
	private void attachElements()
	{
		add(xTitle);
		
		for(JRadioButton radioBtn : xAttributes)
		{
			add(radioBtn);
		}
		
		add(yTitle);
		
		for(JPanel attrPanel : attributePanel)
		{
			add(attrPanel);
		}
		
	}
}
