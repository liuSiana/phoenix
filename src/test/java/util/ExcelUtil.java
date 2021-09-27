package util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/**
	 * 解析Excel文件
	 * @param excelPath Excel文件的存放地址，如果是在项目的根路径下，/文件名
	 * @param sheetIndex sheet索引
	 * @param startRow 开始行
	 * @param endRow 结束行
	 * @param startCell 开始列
	 * @param endCell 结束列
	 * @return 返回二维数组
	 */
	public static Object[][] readExcel(String excelPath,int sheetIndex) {
		XSSFWorkbook workbook=null;
		//1:以输入流的形式加载excel文件
		InputStream is=ExcelUtil.class.getResourceAsStream(excelPath);
		//2：得到工作簿
		try {
			workbook=new XSSFWorkbook(is);
			//3：得到sheet
			XSSFSheet sheet=workbook.getSheetAt(sheetIndex);
			//4：循环得到每一行
			//得到最大行数
			int lastRowNum=sheet.getLastRowNum();
			for(int i=0;i<=lastRowNum;i++){
				Row row=sheet.getRow(i);
				//5：循环得到每一行的每一列
				//得到最大列
				int lastCellNum=row.getLastCellNum();
				for (int j = 0; j < lastCellNum; j++) {
					//6:空单元格按照空字符串进行处理
					Cell cell=row.getCell(j,Row.CREATE_NULL_AS_BLANK);
					//7:单元格所有内容都按照String类型处理
					cell.setCellType(cell.CELL_TYPE_STRING);
					String cellValue=cell.getStringCellValue();
					System.out.print("["+cellValue+"]-");
					//将单元格的值添加到二维数组中
					//datas[？][？]
					//第一行:
						//第一列：data[0][0]
						//第二列：data[0][1]
						//第三列：data[0][2]
					//第二行:
						//第一列：data[1][0]
						//第二列：data[1][1]
						//第三列：data[1][2]
					//由此可以推导出
//					datas[i][j]=cellValue;
				
				}
				System.out.println();
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//示例
	public static void main(String[] args) {
		/*Object[][] datas=readExcel("/register.xlsx",0, 2, 8, 1, 5);
		for (Object[] objects : datas) {
			for (Object object : objects) {
				System.out.print("["+object+"]-");
			}
			System.out.println();
		}*/
		readExcel("/register.xlsx",0);
	
	}

}
