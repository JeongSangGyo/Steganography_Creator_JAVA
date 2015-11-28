
/*
 *작 성 자 : 이준성
 *학    번 : 1401227
 *작성일자 : 2015. 11. 10
 */

/*
 *Import List
 */
import java.io.File;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

/*
 *Steganography_Controller 클래스
 */
public class Steganography_Controller
{
	//프로그램 변수
	private Steganography_View	view;
	private Steganography		model;
	
	//디스프레이 패널
	private JPanel		decode_panel;
	private JPanel		encode_panel;
	//패널 변수
	private JTextArea 	input;
	private JButton		encodeButton,decodeButton;
	private JLabel		image_input;
	//메뉴 변수
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem 	exit;
	
	//액션 이벤트 클래스
	private Encode			enc;
	private Decode			dec;
	private EncodeButton	encButton;
	private DecodeButton	decButton;
	
	//디코딩 변수
	private String			stat_path = "";
	private String			stat_name = "";
	
	/*
	 *뷰와 모델 그리고 환경변수를 초기화 하기 위한 생성자
	 *매개변수 'aView'는 GUI를 다루는 뷰 클래스 오브젝트
	 *매개변수 'aModel'은 모델을 다루는 오브젝트
	 */
	public Steganography_Controller(Steganography_View aView, Steganography aModel)
	{
		//프로그램 변수
		view  = aView;
		model = aModel;
		
		//뷰변수 할당
		//2 뷰
		encode_panel	= view.getTextPanel();
		decode_panel	= view.getImagePanel();
		//2 데이터 옵션
		input			= view.getText();
		image_input		= view.getImageInput();
		//2 버튼
		encodeButton	= view.getEButton();
		decodeButton	= view.getDButton();
		//메뉴
		encode			= view.getEncode();
		decode			= view.getDecode();
		exit			= view.getExit();
		
		//액션 이벤트 할당
		enc = new Encode();
		encode.addActionListener(enc);
		dec = new Decode();
		decode.addActionListener(dec);
		exit.addActionListener(new Exit());
		encButton = new EncodeButton();
		encodeButton.addActionListener(encButton);
		decButton = new DecodeButton();
		decodeButton.addActionListener(decButton);
		
		//인코드 뷰를 최초 뷰로
		encode_view();
	}
	
	/*
	 *인코딩 뷰를 보여주기 위해 패널 업데이트
	 */
	private void encode_view()
	{
		update();
		view.setContentPane(encode_panel);
		view.setVisible(true);
	}
	
	/*
	 *디코딩 뷰를 보여주기 위해 패널 업데이트
	 */
	private void decode_view()
	{
		update();
		view.setContentPane(decode_panel);
		view.setVisible(true);
	}
	
	/*
	 *인코드 내부 클래스 - 인코딩 메뉴 아이템 처리
	 */
	private class Encode implements ActionListener
	{
		/*
		 *클릭 이벤트 처리
		 *매개변수 'e'는 액션 오브젝트
		 */
		public void actionPerformed(ActionEvent e)
		{
			encode_view(); //인코딩 뷰 보여주기
		}
	}
	
	/*
	 *디코드 내부 클래스 - 디코딩 메뉴 아이템 처리
	 */
	private class Decode implements ActionListener
	{
		/*
		 *클릭 이벤트 처리
		 *매개변수 'e'는 액션 오브젝트
		 */
		public void actionPerformed(ActionEvent e)
		{
			decode_view(); //디코드 메뉴 보여주기
			
			//띄어질 파일 경로 선택을 시작
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String image = directory.getPath();
					stat_name = directory.getName();
					stat_path = directory.getPath();
					stat_path = stat_path.substring(0,stat_path.length()-stat_name.length()-1);
					stat_name = stat_name.substring(0, stat_name.length()-4);
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				}
				catch(Exception except) {
				//열기 실패시 메시지 보여주기
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *종료 내부 클래스 - 종료 메뉴 아이템 처리
	 */
	private class Exit implements ActionListener
	{
		/*
		 *클릭 이벤트 처리
		 *매개변수 'e'는 액션 오브젝트
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0); //프로그램 종료
		}
	}
	
	/*
	 *인코드 버튼 내부 클래스 - 인코드 버튼 아이템 처리
	 */
	private class EncodeButton implements ActionListener
	{
		/*
		 *클릭 이벤트 처리
		 *매개변수 'e'는 액션 오브젝트
		 */
		public void actionPerformed(ActionEvent e)
		{
			//띄어질 파일 경로 선택을 시작
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String text = input.getText();
					String ext  = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);
					
					String stegan = JOptionPane.showInputDialog(view,
									"Enter output file name:", "File name",
									JOptionPane.PLAIN_MESSAGE);
					
					if(model.encode(path,name,ext,stegan,text))
					{
						JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					//새로운 이미지 보여주기
					decode_view();
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
				}
				catch(Exception except) {
				//파일 열기 실패시 메시지 보여주기
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	/*
	 *디코드 버튼 내부 클래스 - 디코드 버튼 아이템 처리
	 */
	private class DecodeButton implements ActionListener
	{
		/*
		 *클릭 이벤트 처리
		 *매개변수 'e'는 액션 오브젝트
		 */
		public void actionPerformed(ActionEvent e)
		{
			String message = model.decode(stat_path, stat_name);
			System.out.println(stat_path + ", " + stat_name);
			if(message != "")
			{
				encode_view();
				JOptionPane.showMessageDialog(view, "The Image was decoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
				input.setText(message);
			}
			else
			{
				JOptionPane.showMessageDialog(view, "The Image could not be decoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 *변수들을 초기 상태로 업데이트
	 */
	public void update()
	{
		input.setText("");			//textarea 초기화
		image_input.setIcon(null);	//이미지 초기화
		stat_path = "";				//경로 초기화
		stat_name = "";				//이름 초기화
	}
	
	/*
	 *프로그램 실행을 위한 메인 메서드
	 */
	public static void main(String args[])
	{
		new Steganography_Controller(
									new Steganography_View("Steganography"),
									new Steganography()
									);
	}
}
