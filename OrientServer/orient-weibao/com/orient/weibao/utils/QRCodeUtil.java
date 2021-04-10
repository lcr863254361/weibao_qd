package com.orient.weibao.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import com.swetake.util.Qrcode;

/**
 * 二维码图片生成工具.
 *
 * <p>detailed comment</p>
 * @author [创建人]  WeiLei <br/> 
 * 		   [创建时间] 2017年3月22日 下午5:59:44 <br/> 
 * 		   [修改人]  WeiLei <br/>
 * 		   [修改时间] 2017年3月22日 下午5:59:44
 * @see
 */
public class QRCodeUtil {

	/**二维码图片格式*/
	private static String imgType = "jpg";
	/**二维码尺寸*/
	private static int size = 8;
	/**二维码编码*/
	private String codeType = "utf-8";
	/**二维码中间图片的宽度*/
	private int imageWidth = 30;
	/**二维码中间图片的高度*/
	private int imageHeight = 30;
	/**二维码中间图片路径*/
	private String logoImgPath = "d:\\logo.png";

	public static String getImgType() {
		return imgType;
	}

	public static void setImgType(String imgType) {
		QRCodeUtil.imgType = imgType;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		QRCodeUtil.size = size;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getLogoImgPath() {
		return logoImgPath;
	}

	public void setLogoImgPath(String logoImgPath) {
		this.logoImgPath = logoImgPath;
	}

	/**
	 * 生成二维码(QRCode)图片.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午5:57:16 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午5:57:16
	 * @param content ：存储内容
	 * @param imgPath ：图片路径
	 * @see
	 */
	public void encoderQRCode(String content, String imgPath) {
		this.encoderQRCode(content, imgPath, "jpg", 7);
	}

	/**
	 * 生成二维码(QRCode)图片.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:01:15 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:01:15
	 * @param content ：存储内容
	 * @param imgPath ：图片路径
	 * @param imgType ：图片格式
	 * @see
	 */
	public void encoderQRCode(String content, String imgPath, String imgType) {
		this.encoderQRCode(content, imgPath, imgType, 7);
	}

	/**
	 * 生成二维码(QRCode)图片.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:01:47 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:01:47
	 * @param content ：存储内容
	 * @param imgPath ：图片路径
	 * @param imgType ：图片类型
	 * @param size ： 二维码尺寸
	 * @see
	 */
	public void encoderQRCode(String content, String imgPath, String imgType, int size) {
		
		try {
			BufferedImage bufImg = this.qRCodeCommon(content, size);
			// 在二维码中间加入图片
			//encoderQRLogoImage(bufImg);
			File imgFile = new File(imgPath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码(QRCode)图片的公共方法.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:06:48 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:06:48
	 * @param content ：存储内容
	 * @param imgType ：图片类型
	 * @param size ： 二维码尺寸
	 * @return
	 * @see
	 */
	private BufferedImage qRCodeCommon(String content, int size) {
		
		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = content.toString().getBytes(codeType);
			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
			}
			gs.dispose();
			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImg;
	}

	/**
	 * 解析二维码（QRCode）.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:06:07 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:06:07
	 * @param inputStream 
	 * @return
	 * @see
	 */
	public String decoderQRCode(InputStream inputStream) {
		
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(inputStream);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new QRCodeImageBase(bufImg)), codeType);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		}
		return content;
	}

	/**
	 * 在二维码中间加入图片.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:03:56 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:03:56
	 * @param bufImg
	 * @return
	 * @throws Exception
	 * @see
	 */
	private BufferedImage encoderQRLogoImage(BufferedImage bufImg) throws Exception {
		
		File logoFile = new File(logoImgPath);
		if(logoFile.exists()){
			Image im = ImageIO.read(logoFile);
			Graphics2D g = bufImg.createGraphics();
			// 获取bufImg的中间位置
			int centerX = bufImg.getMinX() + bufImg.getWidth()/2 - imageWidth/2;
			int centerY = bufImg.getMinY() + bufImg.getHeight()/2 - imageHeight/2;
			g.drawImage(im, centerX, centerY, imageWidth, imageHeight, null);
			g.dispose();
			bufImg.flush();
		}
		return bufImg;
	}

	public static void main(String args[]) throws IOException {
		
		String imgPath = "d:/testa.jpg"; // 生成的二维码图片名称
		String encoderContent = "CESHI123456789";
		QRCodeUtil qrcodeUtil = new QRCodeUtil();
		qrcodeUtil.encoderQRCode(encoderContent, imgPath, imgType, 8);
		//System.out.println(qrcodeUtil.decoderQRCode(new FileInputStream(new File(imgPath))));
	}
	
	/**
	 * 二维码基础类.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  WeiLei <br/> 
	 * 		   [创建时间] 2017年3月22日 下午6:13:41 <br/> 
	 * 		   [修改人]  WeiLei <br/>
	 * 		   [修改时间] 2017年3月22日 下午6:13:41
	 * @see
	 */
	public class QRCodeImageBase implements QRCodeImage{
		
		/**BufferedImage作用将一幅图片加载到内存中*/
		private BufferedImage bufImg;

		public QRCodeImageBase(BufferedImage bufImg) {
			this.bufImg = bufImg;
		}  
		
		/**返回像素高度*/
		@Override
		public int getHeight() {
			return bufImg.getHeight();
		}
		
		/**返回像素宽度*/
		@Override
		public int getWidth() {
			return bufImg.getWidth();
		}
		
		/**得到长宽值，即像素值，x,y代表像素值 */
		@Override
		public int getPixel(int x, int y) {
			return bufImg.getRGB(x, y);
		}
	}

}
