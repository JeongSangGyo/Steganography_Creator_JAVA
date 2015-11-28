

/*
 *작 성 자 : 이준성
 *학    번 : 1401227
 *작성일자 : 2015. 11. 1
 */
 
/*
 *Import list
 */
import java.io.File;
 
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
 
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
 
/*
 *Steganography 클래스
 */
public class Steganography
{
     
    /*
     *Steganography 클래스의 빈 생성자
     */
    public Steganography()
    {
    }
     
    /*
     *이미지에 텍스트를 암호화하며 출력 결과물은 .png 파일이다
     *매개변수 'path'		수정할 이미지의 경로이다
     *매개변수 'original'	수정할 이미지의 이름이다
     *매개변수 'ext1'		수정할 이미지의 확장자(jpg, png)이다
     *매개변수 'stegan'		출력 결과 파일의 이름이다
     *매개변수 'message'		이미지에 숨겨질 텍스트이다
     */
    public boolean encode(String path, String original, String ext1, String stegan, String message)
    {
        String          file_name   = image_path(path,original,ext1);
        BufferedImage   image_orig  = getImage(file_name);
         
        //암호화를 위한 이미지를 메모리상에 복사
        BufferedImage image = user_space(image_orig);
        image = add_text(image,message);
         
        return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
    }
     
    /*
     *해독시 이미지 타입은 .png로 가정하여 진행하며, 이미지에서 숨겨진 텍스트를 추출한다
     *매개변수 'path'	메시지를 추출하기 위한 이미지의 경로
     *매개변수 'name'	메시지를 추출하기 위한 이미지의 이름
     */
    public String decode(String path, String name)
    {
        byte[] decode;
        try
        {
            //해독을 위한 이미지를 메모리 상에 복사
            BufferedImage image  = user_space(getImage(image_path(path,name,"png")));
            decode = decode_text(get_byte_data(image));
            return(new String(decode));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
                "There is no hidden message in this image!","Error",
                JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
     
    /*
     *완전한 파일 경로 반환: 'path/name.ext'
     *매개변수 path	파일의 경로
     *매개변수 name	파일의 이름
     *매개변수 ext	파일의 확장자
     *반환 값 완전한 파일의 경로를 문자열 오브젝트로 반환
     */
    private String image_path(String path, String name, String ext)
    {
        return path + "/" + name + "." + ext;
    }
     
    /*
     *이미지 파일을 얻기 위한 메서드
     *매개변수 'f' 이미지의 완전한 파일 경로
     *반환 값 제공된 파일 경로의 버퍼이미지
     *참조  Steganography.image_path
     */
    private BufferedImage getImage(String f)
    {
        BufferedImage   image   = null;
        File			file    = new File(f);
         
        try
        {
            image = ImageIO.read(file);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,
                "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }
     
    /*
     *이미지 파일 저장을 위한 메서드
     *매개변수 image		저장할 이미지파일
     *매개변수 file		저장될 파일 객체
     *매개변수 ext		저장할 파일의 확장자
     *반환 값 저장 성공시 true 반환
     */
    private boolean setImage(BufferedImage image, File file, String ext)
    {
        try
        {
            file.delete(); //이전 객체를 삭제
            ImageIO.write(image,ext,file);
            return true;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
                "File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
    /*
     *이미지에 텍스트를 암호화 처리하는 메서드
     *매개변수 'image'	텍스트를 숨길 이미지
     *매개변수 'text'	이미지에 숨겨질 텍스트
     *반환 값 텍스트가 암호화 된 이미지를 반환
     */
    private BufferedImage add_text(BufferedImage image, String text)
    {
        //넘겨받은 모든 객체를 바이트 배열로 변환: 이미지, 메시지, 메시지의 길이
        byte img[]  = get_byte_data(image);
        byte msg[] = text.getBytes();
        byte len[]   = bit_conversion(msg.length);
        try
        {
            encode_text(img, len,  0); //0 위치 초기화
            encode_text(img, msg, 32); //4 바이트 만큼을 문장 길이 표현을 위해 할당: 4bytes*8bit = 32 bits
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }
     
    /*
     *이미지 변환 및 저장을 위한 사용자 정의 이미지 생성
     *매개변수 변환을 위한 이미지
     *반환 값 변환 된 이미지
     */
    private BufferedImage user_space(BufferedImage image)
    {
        //제공 받은 이미지로 새로운 이미지 생성
        BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D  graphics = new_img.createGraphics();
        graphics.drawRenderedImage(image, null);
        graphics.dispose(); //사용 완료한 객체를 메모리에서 제거
        return new_img;
    }
     
    /*
     *이미지의 바이트 배열을 구하는 메서드
     *매개변수 'image' 바이트 데이터를 얻기 위한 이미지
     *반환 값 제공된 이미지의 바이트 배열을 반환
     *참조 Raster
     *참조 WritableRaster
     *참조 DataBufferByte
     */
    private byte[] get_byte_data(BufferedImage image)
    {
        WritableRaster raster   = image.getRaster();
        DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
        return buffer.getData();
    }
     
    /*
     *정수를 2진수로 변환
     *매개변수 i 변환할 정수
     *반환 값 4개의 2진수 요소를 가진 바이트 배열 반환
     */
    private byte[] bit_conversion(int i)
    {
        //byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
        //byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
        //byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
        //byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
         
        //4바이트 만 사용
        byte byte3 = (byte)((i & 0xFF000000) >>> 24);
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16);
        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 );
        byte byte0 = (byte)((i & 0x000000FF)       );
        return(new byte[]{byte3,byte2,byte1,byte0});
    }
     
    /*
     *제공 받은 오프셋부터 시작하여 하나의 바이트 배열에 다른 바이트 베열을 인코딩
     *매개변수 'image'		이미지 데이터 배열
     *매개변수 'addition'	이미지에 추가될 배열
     *매개변수 'offset'		이미지 배열에 다른 배열을 삽입하는 시작 지점
     *반환 값 변환된 이미지 데이터 배열 반환
     */
    private byte[] encode_text(byte[] image, byte[] addition, int offset)
    {
        //데이터와 시작 지점이 이미지 크기를 넘지 않은지 확인
        if(addition.length + offset > image.length)
        {
            throw new IllegalArgumentException("File not long enough!");
        }
        //추가할 배열의 크기 만큼 반복
        for(int i=0; i<addition.length; ++i)
        {
            //8비트를 삽입하기 위한 반복
            int add = addition[i];
            for(int bit=7; bit>=0; --bit, ++offset)
            {
                int b = (add >>> bit) & 1;
                
                image[offset] = (byte)((image[offset] & 0xFE) | b );
            }
        }
        return image;
    }
     
    /*
     *숨겨진 텍스트를 이미지에서 추출
     *매개변수 'image'	이미지의 바이트 데이터 배열
     *반환 값 숨겨진 택스트의 바이트 데이터 배열 반환
     */
    private byte[] decode_text(byte[] image)
    {
        int length = 0;
        int offset  = 32;
        //텍스트의 길이를 확인하기 위해 32바이트 만큼 반복
        for(int i=0; i<32; ++i)
        {
            length = (length << 1) | (image[i] & 1);
        }
         
        byte[] result = new byte[length];
         
        //텍스트의 각 바이트 마다 반복
        for(int b=0; b<result.length; ++b )
        {
            //텍스트의 각 비트를 추출하기 위해 반복
            for(int i=0; i<8; ++i, ++offset)
            {
                result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
            }
        }
        return result;

    }
}
