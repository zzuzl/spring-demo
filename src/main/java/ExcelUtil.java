import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglei53 on 2017/11/3.
 */
public class ExcelUtil {

    private static void writeFile(String filePath, Workbook workbook) {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Workbook buildWorkBook() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = sheet.createRow((short)0);
        row.createCell(3).setCellValue("dawdaw");

        return wb;
    }

    private static List<Item> readFile(String filePath) {
        List<Item> items = new ArrayList<Item>();
        NPOIFSFileSystem fs = null;
        try {
            fs = new NPOIFSFileSystem(new File(filePath));
            HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(), true);
            HSSFSheet sheet = wb.getSheetAt(0);
            for(Row row : sheet) {
                Cell cell = row.getCell(0);
                if(cell.getCellType() == CellType.STRING.getCode()) {
                    System.out.println(cell.getRichStringCellValue().getString());
                } else if(cell.getCellType() == CellType.NUMERIC.getCode()) {
                    System.out.println(cell.getNumericCellValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public static void main(String[] args) {
        String outputFilePath = "D:/1.xls";
        String inputFilePath = "D:/2.xls";
        Workbook wb = buildWorkBook();
        // writeFile(filePath, wb);

        readFile(inputFilePath);
    }
}