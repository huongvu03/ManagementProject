package Database;

import Models.Bill;
import Models.Customer;
import Models.Order;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DashBoardDAO {

    public Alert alert;
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

    public ArrayList<Bill> listDB(String from, String to) {
        ArrayList<Bill> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Bill WHERE Bill.billStatus = 'PAID'");

        if (from != null && to != null) {
            sql.append(" AND billDate BETWEEN '").append(from).append("' AND '").append(to).append("'");
        } else if (from != null) {
            sql.append(" AND billDate >= '").append(from).append("'");
        } else if (to != null) {
            sql.append(" AND billDate <= '").append(to).append("'");
        }

        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql.toString());
            while (rs.next()) {
                Bill pro = new Bill();
                pro.setBillId(rs.getInt("billId"));
                pro.setTableNo(rs.getString("tableNo"));
                pro.setGuestNo(rs.getInt("guestNo"));
                pro.setCus_id(rs.getString("cus_id"));
                pro.setUserName(rs.getString("userName"));
                pro.setBillTotal(rs.getDouble("billTotal"));
                pro.setBillDiscount(rs.getDouble("billDiscount"));
                pro.setBillTax(rs.getDouble("billTax"));
                pro.setBillService(rs.getDouble("billService"));
                pro.setBillSubTotal(rs.getDouble("billSubTotal"));
                pro.setBillDate(rs.getDate("billDate"));
                pro.setBillStatus(rs.getString("billStatus"));
                list.add(pro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Order> listOrder() {
        ArrayList<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from OrderArchive");

        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql.toString());
            while (rs.next()) {
                Order pro = new Order();
                pro.setBillId(rs.getInt("billId"));
                pro.setProId(rs.getString("proId"));
                pro.setProName(rs.getString("proName"));
                pro.setCateId(rs.getInt("cateId"));
                pro.setQuantity(rs.getInt("quantity"));
                pro.setProPrice(rs.getDouble("proPrice"));
                list.add(pro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Order> listTable(String from, String to) {
        ArrayList<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT OrderArchive.proId, OrderArchive.proName, SUM(OrderArchive.quantity) AS quantity, SUM(OrderArchive.proPrice) AS totalProPrice, Bill.billStatus FROM OrderArchive JOIN Bill ON OrderArchive.billId = Bill.billId WHERE Bill.billStatus = 'PAID'");

        if (from != null && to != null) {
            sql.append(" AND billDate BETWEEN '").append(from).append("' AND '").append(to).append("'");
        } else if (from != null) {
            sql.append(" AND billDate >= '").append(from).append("'");
        } else if (to != null) {
            sql.append(" AND billDate <= '").append(to).append("'");
        }

        sql.append(" GROUP BY OrderArchive.proId, OrderArchive.proName, Bill.billStatus ORDER BY quantity DESC;");

        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql.toString());
            while (rs.next()) {
                Order pro = new Order();
                pro.setProId(rs.getString("proId"));
                pro.setProName(rs.getString("proName"));
                pro.setQuantity(rs.getInt("quantity"));
                pro.setProPrice(rs.getDouble("totalProPrice"));

                list.add(pro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void exportAndOpenExcelWithTemplate2(List<Bill> ExBills, List<Order> ExBOrders) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Product");

        // Define styles for header and data cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 15);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font dataFont = workbook.createFont();
        dataFont.setFontHeightInPoints((short) 14);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setFont(dataFont);
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);

        CellStyle formatStyle = workbook.createCellStyle();
        formatStyle.cloneStyleFrom(borderStyle);
        formatStyle.setFont(dataFont);
        DataFormat format = workbook.createDataFormat();
        formatStyle.setDataFormat(format.getFormat("#,##0.0"));

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(borderStyle);
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));

        Row headerRow = sheet.createRow(0);
        String[] columns = {"Bill ID", "Guest No", "Cus Id", "Pro ID", "Pro Name", "Cate ID", "Quantity", "Price", "Bill Total", "Discount", "Service", "Tax", "Bill Sub Total", "Bill Date"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        double billTotal = 0.0;
        double discount = 0.0;
        double service = 0.0;
        double tax = 0.0;
        double subTotal = 0.0;
        for (Bill bill : ExBills) {
            int startRow = rowNum;
            int orderCount = 0;
            for (Order order : ExBOrders) {
                if (order.getBillId() == bill.getBillId()) {
                    Row orderRow = sheet.createRow(rowNum++);
                    createCellWithStyle(orderRow, 3, order.getProId(), borderStyle);
                    createCellWithStyle(orderRow, 4, order.getProName(), borderStyle);
                    createCellWithStyle(orderRow, 5, order.getCateId(), borderStyle);
                    createCellWithStyle(orderRow, 6, order.getQuantity(), borderStyle);
                    createCellWithStyle(orderRow, 7, order.getProPrice(), formatStyle);
                    orderCount++;
                }
            }

            // Set Bill details in the first row of the merged region
            Row firstOrderRow = sheet.getRow(startRow);
            if (firstOrderRow == null) {
                firstOrderRow = sheet.createRow(startRow);
            }
            createCellWithStyle(firstOrderRow, 0, bill.getBillId(), borderStyle);
            createCellWithStyle(firstOrderRow, 1, bill.getGuestNo(), borderStyle);
            createCellWithStyle(firstOrderRow, 2, bill.getCus_id(), borderStyle);
            createCellWithStyle(firstOrderRow, 8, bill.getBillTotal(), formatStyle);
            createCellWithStyle(firstOrderRow, 9, bill.getBillDiscount(), formatStyle);
            createCellWithStyle(firstOrderRow, 10, bill.getBillService(), formatStyle);
            createCellWithStyle(firstOrderRow, 11, bill.getBillTax(), formatStyle);
            createCellWithStyle(firstOrderRow, 12, bill.getBillSubTotal(), formatStyle);
            createCellWithStyle(firstOrderRow, 13, bill.getBillDate().toString(), dateStyle); // Assuming getBillDate() returns a Date

            billTotal += bill.getBillTotal();
            discount += bill.getBillDiscount();
            service += bill.getBillService();
            tax += bill.getBillTax();
            subTotal += bill.getBillSubTotal();

            // Merge cells for Bill information
            if (orderCount > 1) {
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 0, 0, borderStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 1, 1, borderStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 2, 2, borderStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 8, 8, formatStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 9, 9, formatStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 10, 10, formatStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 11, 11, formatStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 12, 12, formatStyle);
                mergeCellsAndApplyStyle(sheet, startRow, startRow + orderCount - 1, 13, 13, dateStyle);
            }
        }

        Row totalRow = sheet.createRow(rowNum);
        createCellWithStyle(totalRow, 7, "Total :", borderStyle);
        createCellWithStyle(totalRow, 8, billTotal, formatStyle);
        createCellWithStyle(totalRow, 9, discount, formatStyle);
        createCellWithStyle(totalRow, 10, service, formatStyle);
        createCellWithStyle(totalRow, 11, tax, formatStyle);
        createCellWithStyle(totalRow, 12, subTotal, formatStyle);

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialFileName("Product List.xlsx");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                System.out.println("Excel file saved successfully!");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException e) {
                System.out.println("Error saving or opening Excel file: " + e.getMessage());
            }
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCellWithStyle(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }
        cell.setCellStyle(style);
    }

    private void mergeCellsAndApplyStyle(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        for (int row = firstRow; row <= lastRow; row++) {
            Row sheetRow = sheet.getRow(row);
            if (sheetRow == null) {
                sheetRow = sheet.createRow(row);
            }
            for (int col = firstCol; col <= lastCol; col++) {
                Cell cell = sheetRow.getCell(col);
                if (cell == null) {
                    cell = sheetRow.createCell(col);
                }
                cell.setCellStyle(style);
            }
        }
    }

}
