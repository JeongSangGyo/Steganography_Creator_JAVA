
/*
 *작성자 : 정상교
 *학번 : 1201270
 */


import java.io.*; 


public class Image_Filter extends javax.swing.filechooser.FileFilter
{
	/*
	 *파일의 확장자가 정의된 값과 같은지에 관한 여부 
	 *매개변수 ext 파일의 확장자
	 *return 파일의 확장자가 'jpg' 혹은 'png' 일때 true를 반환
	 */
	protected boolean isImageFile(String ext)
	{
		return (ext.equals("jpg")||ext.equals("png"));
	}
	
	/*
	 *선택된 객체가 폴더 또는 사용가능한 확장자인지 확인
	 *매개변수 f 선택한 폴더 실행/적절한 확장자인지 확인
	 *return 선택한 객체가 폴더 또는 허용가능한 확장자인 경우 true를 반환
	 */
	public boolean accept(File f)
	{
	    if (f.isDirectory())
	    {
			return true;
	    }

	    String extension = getExtension(f);
		if (extension.equals("jpg")||extension.equals("png"))
		{
			return true;
		}
		return false;
	}
	
	/*
	 *선택된 파일의 형식 설명
	 *return 메세지를 반환
	 */
	public String getDescription()
	{
		return "Supported Image Files";
	}
	
	/*
	 *확장자를 확인
	 *매개변수 f 파일의 확장자를 반환하기 위한 매개변수
	 *return 확장자명을 나타내는 문자열 반환
	 */
	protected static String getExtension(File f)
	{
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1) 
		  return s.substring(i+1).toLowerCase();
		return "";
	}	
}
