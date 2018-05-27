/*package ch.softi.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.mybatis.generator.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import ch.softi.bean.Msg;
import ch.softi.bean.Person;
import ch.softi.bean.WordGenerator;
import ch.softi.bean.WordImport;
import ch.softi.service.PersonService;


@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
		
	@RequestMapping("/list")
	public String getPersonList(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model){				
		List<Person> person = personService.getAllPerson();		
		model.addAttribute("personlist",person);
		return "list";
	}
	
	//excel 瀵煎嚭
	@RequestMapping("/exportExcel")
	public void getexcelList(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model,HttpServletResponse response){				
		System.out.println("----------------");
		List<Person> person = personService.getAllPerson();		
		//鍒涘缓HSSFWorkbook瀵硅薄
		HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet createSheet = wb.createSheet("sheet01");
			//璁剧疆鎵�鏈夊崟鍏冩牸鐨勮楂樺拰鍒楀
			createSheet.setDefaultRowHeightInPoints(34);
			createSheet.setDefaultColumnWidth(20);
			
			//createSheet.setColumnWidth(0, 256 * 50);//璁剧疆鍗曠嫭鐨勫垪鐨勫
			HSSFCellStyle createCellStyle = wb.createCellStyle();//璁剧疆鍗曞厓鏍肩殑鏍煎紡
			createCellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			createCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//鍒涘缓HSSFCell瀵硅薄
			HSSFRow createRow = createSheet.createRow(1);
			HSSFCell createCell = createRow.createCell(2);
			HSSFRow createRow01 = createSheet.createRow(2);
			HSSFCell createCell01 = createRow01.createCell(4);
			createSheet.addMergedRegion(new CellRangeAddress(1,2,2,3));//鍚堝苟鍗曞厓鏍艰捣濮嬭鍙凤紝缁堟琛屽彿锛� 璧峰鍒楀彿锛岀粓姝㈠垪鍙�
			//璁剧疆鍗曞厓鏍肩殑鍊�
			createCell.setCellValue("鍗曞厓鏍间腑鐨勪腑鏂�");
			createCell01.setCellValue("鍚堝苟鍗曞厓鏍�");
			
			
			//FileOutputStream output = null;
			OutputStream output = null;
			try {
				output = response.getOutputStream();
				response.reset();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String excelName = format.format(new Date());
				String filedisplay = "JHGL-" + excelName + ".xls";
				 //String filedisplay = "杩樻璁″垝.xls";
				 filedisplay = new String( filedisplay.getBytes("utf-8"), "ISO8859-1" );//ISO8859-1 鏄〉闈㈢殑浼犺緭绫诲瀷
				response.setHeader("Content-Disposition", "attachment;filename="+ filedisplay);
				response.setContentType("Content-Type:application/vnd.ms-excel");
				
				//output=new FileOutputStream("d:\\workbook.xls");
				wb.write(output);
				output.flush();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				try {
					wb.close();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		//	Content type 'application/octet-stream' not supported
	}
	
	//excel 瀵煎叆
	@RequestMapping( value ="/importExcel" ,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Msg excledr(HttpServletResponse response,HttpServletRequest request,@RequestParam(value = "file",required = false)MultipartFile file){
		
		response.setCharacterEncoding("utf-8");
		try {
			String temPath = request.getSession().getServletContext().getRealPath("WEB-INF/tem");
			System.out.println(temPath);
			//鍒涘缓涓�涓枃浠�
			File temfile = new File(temPath);
			//鍒ゆ柇鏂囦欢鏄惁瀛樺湪
			if(!temfile.exists()){
				//鍒涘缓鏂囦欢澶�
				temfile.mkdirs();
			}
			
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			
			List item = upload.parseRequest(request);
			if(item.isEmpty())
				return Msg.success();
			
			FileItem fi = (FileItem)item.get(0);
			//寰楀埌瀛樻枃浠剁殑鏂囦欢鍚嶇О
			System.out.println(fi.getName().indexOf("xls")+"=====");
			if(fi.getName().indexOf("xls") == -1 || fi.getName().indexOf("xlsx") == -1){
				return Msg.success();
			}
			
			String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fi.getName();
			InputStream in = fi.getInputStream();
			FileOutputStream out = new FileOutputStream(temPath + "\\" + saveFileName);
			//鍒涘缓涓�涓紦鍐插尯
			byte buffer[] = new byte[1024];
			//鍒ゆ柇杈撳叆娴佷腑鐨勬暟鎹槸鍚﹀凡缁忚瀹�
			int len = 0;
			while((len = in.read(buffer)) > 0){
				out.write(buffer,0,len);
			}
			
			out.close();
			in.close();
			
			System.out.println(item.get(0).toString()+"------");
			//InputStream fileIn = request.getInputStream();
			//inputStream.reset();
			FileInputStream fileIn = new FileInputStream(temPath + "\\" + saveFileName);
			System.out.println(temPath + "\\" + saveFileName);
			//鏍规嵁鎸囧畾鐨勬枃浠惰緭鍏ユ祦瀵煎叆Excel浠庤�屼骇鐢焀orkbook瀵硅薄
			Workbook wb0 = new HSSFWorkbook(fileIn);
			//鑾峰緱鐨別xcle鐨勭涓�涓〃鍗�
			Sheet sht0 = wb0.getSheetAt(0);		
			
			Row row = sht0.getRow(2);
			Cell cell = row.getCell(2);
			String stringCellValue = cell.getStringCellValue();
		System.out.println(stringCellValue+"------");
		
		
		fileIn.close();
		wb0.close();
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return Msg.success();
	}
	
	
	//word鏂囨。鐨勫鍑�
	@RequestMapping("/word")
	public void createDoc(){
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("rq", "2018-1");
		map.put("rinian", "2018");
		map.put("rqyue", "1");
		map.put("rqri", "12");
		map.put("dwy", "20000");
		map.put("gysmc", "閲嶅簡");
		map.put("erpbm", "123456");
		map.put("khyh", "123");
		map.put("lhh", "22213");
		map.put("yhzh", "123123123");
		map.put("name", "椤圭洰缂栧彿");
		map.put("xmmc", "閲嶅簡绠℃帶");
		map.put("wbsbm", "1231243242325");
		map.put("xmlb", "杈撶數");
		map.put("sfyjgjs", "鏄�");
		map.put("fgtj", "鏀粯瀹�");
		map.put("fwcgddh", "12321432");
		map.put("yfje", "1000000");
		map.put("bcfkje", "10000");
		map.put("ljfkje", "12113");
		map.put("kzbj", "20000");
		map.put("bz", "鍒樺");
		WordGenerator.createDoc(map, "word");	
	}
	
	//word鏂囨。瀵煎叆
	@RequestMapping("/worddr")
	    public void testWord(String filePath){  
			 filePath = "d:\\ccccccworkbook.docx";
			 List wordIn = WordImport.wordIn(filePath);
			 for (int i = 0; i < wordIn.size(); i++) {
				System.out.println(wordIn.get(i)+"-----");
			}	        
	    }  
	
	@RequestMapping("/printExcel")
	public void printExcel(){
		File f = new File("D:\\XMFK-20180202145322.xls");
		 if (f != null && f.exists()) {
	            String fileNameString = f.getName();
	           String postfixString = "xls";
	            if (postfixString.equalsIgnoreCase("xls") || postfixString.equalsIgnoreCase("xlsx")) {
	                *//**
	                 * 鍔熻兘:瀹炵幇excel鎵撳嵃宸ヤ綔
	                 *//*
	                ComThread.InitSTA();
	                ActiveXComponent xl = new ActiveXComponent("Excel.Application");
	                try {
	                    // System.out.println("version=" +
	                    // xl.getProperty("Version"));
	                    // 涓嶆墦寮�鏂囨。
	                    Dispatch.put(xl, "Visible", new Variant(false));
	                    Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
	                    // 鎵撳紑鏂囨。
	                    Dispatch excel = Dispatch.call(workbooks, "Open", f.getAbsolutePath()).toDispatch();
	                    // 妯悜鎵撳嵃(2013/05/24)
	                    // Dispatch currentSheet = Dispatch.get(excel,
	                    // "ActiveSheet")
	                    // .toDispatch();
	                    // Dispatch pageSetup = Dispatch
	                    // .get(currentSheet, "PageSetup").toDispatch();
	                    // Dispatch.put(pageSetup, "Orientation", new Variant(2));
	                    // 姣忓紶琛ㄩ兘妯悜鎵撳嵃2013-10-31
	                    Dispatch sheets = Dispatch.get((Dispatch) excel, "Sheets").toDispatch();
	                    // 鑾峰緱鍑犱釜sheet
	                    int count = Dispatch.get(sheets, "Count").getInt();
	                    // System.out.println(count);
	                    for (int j = 1; j <= count; j++) {
	                        Dispatch sheet = Dispatch
	                                .invoke(sheets, "Item", Dispatch.Get, new Object[] { new Integer(j) }, new int[1])
	                                .toDispatch();
	                        Dispatch pageSetup = Dispatch.get(sheet, "PageSetup").toDispatch();
	                        Dispatch.put(pageSetup, "Orientation", new Variant(2));
	                        Dispatch.call(sheet, "PrintOut");
	                    }
	                    // 寮�濮嬫墦鍗�
	                    if (excel != null) {
	                        // Dispatch.call(excel, "PrintOut");
	                        // 澧炲姞浠ヤ笅涓夎浠ｇ爜瑙ｅ喅鏂囦欢鏃犳硶鍒犻櫎bug
	                        Dispatch.call(excel, "save");
	                        Dispatch.call(excel, "Close", new Variant(true));
	                        excel = null;
	                    }
	                    xl.invoke("Quit", new Variant[] {});
	                    xl = null;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    // 濮嬬粓閲婃斁璧勬簮
	                    ComThread.Release();
	                }
	            } 
	            
	            else if (postfixString.equalsIgnoreCase("doc") || postfixString.equalsIgnoreCase("docx")) {
	                ComThread.InitSTA();
	                ActiveXComponent wd = new ActiveXComponent("Word.Application");
	                try {
	                    // 涓嶆墦寮�鏂囨。
	                    Dispatch.put(wd, "Visible", new Variant(false));
	                    Dispatch document = wd.getProperty("Documents").toDispatch();
	                    // 鎵撳紑鏂囨。
	                    Dispatch doc = Dispatch
	                            .invoke(document, "Open", Dispatch.Method, new Object[] { f.getAbsolutePath() }, new int[1])
	                            .toDispatch();
	                    // 寮�濮嬫墦鍗�
	                    if (doc != null) {
	                        Dispatch.call(doc, "PrintOut");
	                        // 澧炲姞浠ヤ笅涓夎浠ｇ爜瑙ｅ喅鏂囦欢鏃犳硶鍒犻櫎bug
	                        Dispatch.call(doc, "save");
	                        Dispatch.call(doc, "Close", new Variant(true));
	                        doc = null;
	                    }
	                    wd.invoke("Quit", new Variant[] {});
	                    wd = null;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    // 濮嬬粓閲婃斁璧勬簮
	                    ComThread.Release();
	                }
	            }
	            
	            else {
	            }
	        } else {
	        }
		
		
	}
	
}
*/