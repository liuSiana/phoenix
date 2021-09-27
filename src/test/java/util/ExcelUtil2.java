package util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil2 {
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
	public static Object[][] readExcel(String excelPath,int sheetIndex,int startRow,int endRow,int startCell,int endCell) {
		//创建一个二维数组用来存放数据
		//从第1行到第3行--》一共是3行--》3-1+1=3
		//从第3行到第7行--》一共是5行--》7-3+1=5
		//列也是一样
		//由此得到几行几列
		Object[][] datas=new Object[endRow-startRow+1][endCell-startCell+1];
		//1:以输入流的形式加载excel文件
		InputStream is=ExcelUtil2.class.getResourceAsStream(excelPath);
		//2：得到工作簿
		try {
			Workbook workbook=WorkbookFactory.create(is);
			//3：得到sheet
			Sheet sheet=workbook.getSheetAt(sheetIndex);
			//4：循环得到每一行
			for(int i=startRow;i<=endRow;i++){
				Row row=sheet.getRow(i-1);
				//5：循环得到每一行的每一列
				for (int j = startCell; j <= endCell; j++) {
					//6:空单元格按照空字符串进行处理
					Cell cell=row.getCell(j-1,Row.CREATE_NULL_AS_BLANK);
					//7:单元格所有内容都按照String类型处理
					cell.setCellType(cell.CELL_TYPE_STRING);
					String cellValue=cell.getStringCellValue();
//					System.out.print("["+cell.getStringCellValue()+"]-");
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
					datas[i-startRow][j-startCell]=cellValue;
				
				}
//				System.out.println();
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	//示例
	public static void main(String[] args) {
		Object[][] datas=readExcel("/register.xlsx",0, 2, 8, 1, 5);
		for (Object[] objects : datas) {
			for (Object object : objects) {
				System.out.print("["+object+"]-");
			}
			System.out.println();
		}
	}

}
