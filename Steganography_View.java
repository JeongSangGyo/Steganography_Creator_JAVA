
/*
 *작성자 : 정상교
 *학번 : 1201270
 */


import java.awt.Color;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;


public class Steganography_View extends JFrame
{
	//윈도우창의 크기를 정하는 변수
	private static int WIDTH  = 500;
	private static int HEIGHT = 400;
	
	//JPanel의 요소
	private JTextArea 	input;
	private JScrollBar 	scroll,scroll2;
	private JButton		encodeButton,decodeButton;
	private JLabel		image_input;
	
	//메뉴버튼에 들어갈 요소
	private JMenu 		file;
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem 	exit;
	
	/*
	 *Steganography_View 클래스 생성자
	 *매개변수 name JFrame의 제목을 설정
	 */
	public Steganography_View(String name)
	{
		//JFrame 제목 설정
		super(name);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("파일");	file.setMnemonic('F');
		encode = new JMenuItem("인코드"); encode.setMnemonic('E'); file.add(encode);
		decode = new JMenuItem("디코드(파일선택)"); decode.setMnemonic('D'); file.add(decode);
		file.addSeparator();
		exit = new JMenuItem("종료"); exit.setMnemonic('x'); file.add(exit);
		
		menu.add(file);
		setJMenuBar(menu);
		
		setResizable(true);						//윈도우 사이즈 변경 가능여부 : true?false
		setBackground(Color.lightGray);			//배경색 설정: Color(int,int,int) or Color.name
		setLocation(100,100);					//실행시 윈도우가 나타나는 위치
        setDefaultCloseOperation(EXIT_ON_CLOSE);//종료버튼을 눌렀을 때 실행 종료: exit, do_nothing, etc
        setSize(WIDTH,HEIGHT);					//윈도우창의 크기 설정
        setVisible(true);						//윈도우창 표시여부 : true?false
	}
	
	/*
	 *return The menu item 'Encode'
	 */
	public JMenuItem	getEncode()		{ return encode;			}
	/*
	 *return The menu item 'Decode'
	 */
	public JMenuItem	getDecode()		{ return decode;			}
	/*
	 *return The menu item 'Exit'
	 */
	public JMenuItem	getExit()		{ return exit;				}
	/*
	 *return The TextArea containing the text to encode
	 */
	public JTextArea	getText()		{ return input;				}
	/*
	 *return The JLabel containing the image to decode text from
	 */
	public JLabel		getImageInput()	{ return image_input;		}
	/*
	 *return The JPanel displaying the Encode View
	 */
	public JPanel		getTextPanel()	{ return new Text_Panel();	}
	/*
	 *return The JPanel displaying the Decode View
	 */
	public JPanel		getImagePanel()	{ return new Image_Panel();	}
	/*
	 *return The Encode button
	 */
	public JButton		getEButton()	{ return encodeButton;		}
	/*
	 *@return The Decode button
	 */
	public JButton		getDButton()	{ return decodeButton;		}
	
	
	private class Text_Panel extends JPanel
	{
		/*
		 *인코딩시 들어갈 텍스트에 관한 생성자
		 */
		public Text_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			input = new JTextArea();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll = new JScrollPane(input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll,layoutConstraints);
			scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    	add(scroll);
	    	
	    	encodeButton = new JButton("인코드(파일선택)");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(encodeButton,layoutConstraints);
	    	add(encodeButton);
	    	
	    	//기본 디스플레이 설정
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		}
	}
	

	private class Image_Panel extends JPanel
	{
		/*
		 *디코드 된 이미지를 표시하기 위한 생성자
		 */
		public Image_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    	add(scroll2);
	    	
	    	decodeButton = new JButton("디코드");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(decodeButton,layoutConstraints);
	    	add(decodeButton);
	    	
	    	//기본 디스플레이 설정
			setBackground(Color.red);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }
}
