package managementproject;

import Database.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.chart.XYChart;

public class DashBoardController implements Initializable {

    @FXML
    private Label totalSale;
    @FXML
    private Label totalCustomer;
    @FXML
    private Label totalRevenue;
    @FXML
    private AreaChart<String, Number> areaChart1;
    @FXML
    private CategoryAxis xAxis1;
    @FXML
    private AreaChart<?, ?> areaChart2;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private RadioButton ratioDay;
    @FXML
    private RadioButton ratioMonth;
    @FXML
    private RadioButton ratioYear;

    private ConnectDB db = new ConnectDB();
    private Connection connectDb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectDb = db.GetConnectDB();
        if (connectDb != null) {
            getTotalData();
            ratioDay.setSelected(true);
            getDataByDay();
        } else {
            System.err.println("Database connection failed.");
        }
    }

    private void getTotalData() {
        try (Statement statement = connectDb.createStatement()) {
            String getTotalSaleQuery = "SELECT COUNT(proId) AS total_sale FROM Product;";
            ResultSet queryResult1 = statement.executeQuery(getTotalSaleQuery);
            if (queryResult1.next()) {
                totalSale.setText(queryResult1.getString("total_sale"));
            }
            queryResult1.close();

            String getTotalCustomerQuery = "SELECT COUNT(cateId) AS total_customer FROM Category;";
            ResultSet queryResult2 = statement.executeQuery(getTotalCustomerQuery);
            if (queryResult2.next()) {
                totalCustomer.setText(queryResult2.getString("total_customer"));
            }
            queryResult2.close();

            String getTotalRevenueQuery = "SELECT COUNT(userId) AS total_earn FROM UserInfo;";
            ResultSet queryResult3 = statement.executeQuery(getTotalRevenueQuery);
            if (queryResult3.next()) {
                totalRevenue.setText(queryResult3.getString("total_earn"));
            }
            queryResult3.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private void getDataByDay() {
        loadData("Day of the Month", "Earning Per Day", "Sales Per Day", LocalDate.now().getMonth().name(),0, LocalDate.now().getDayOfMonth(), "day", String.valueOf(LocalDate.now().getYear())+"-07-","");
    }

    private void getDataByMonth() {
        loadData("Month of the Year", "Earning Per Month", "Sales Per Month", String.valueOf(LocalDate.now().getYear()),0, LocalDate.now().getMonthValue(), "month", String.valueOf(LocalDate.now().getYear())+"-","-%");
    }

    private void getDataByYear() {
        loadData("Year", "Earning Per Year", "Sales Per Year", "per Year",2020, LocalDate.now().getYear(), "year","","-%");
    }

    private void loadData(String xAxisLabel, String series1Name, String series2Name, String title,int minPeriod, int maxPeriod, String periodType, String dateFormat1,String dateFormat2) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        //XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        areaChart1.getData().clear();
        areaChart1.setTitle("Sales in " + title);
        series1.setName(series1Name);
        //series2.setName(series2Name);
        xAxis1.setLabel(xAxisLabel);
        //xAxis1.autosize();
        xAxis1.setAutoRanging(false);
ObservableList<String> categories = FXCollections.observableArrayList();
        for (int period =minPeriod; period <= maxPeriod; period++) {
            String formattedPeriod = (period < 10 ? "0" : "") + period;
            categories.add(formattedPeriod);
            try (Statement statement = connectDb.createStatement()) {
                String getSaleQuery = "SELECT SUM(proPrice) AS sale FROM Product WHERE proDate LIKE '" + dateFormat1 + formattedPeriod + dateFormat2 +"';";
                ResultSet queryResult1 = statement.executeQuery(getSaleQuery);
                double sale = 0;
                if (queryResult1.next()) {
                    sale = queryResult1.getDouble("sale");
                }
                queryResult1.close();

//                String getCupsQuery = "SELECT COUNT(userId) AS cups FROM UserInfo WHERE userDate LIKE '" + dateFormat + formattedPeriod + "%';";
//                ResultSet queryResult2 = statement.executeQuery(getCupsQuery);
//                int cups = 0;
//                if (queryResult2.next()) {
//                    cups = queryResult2.getInt("cups");
//                }
//                queryResult2.close();

                //series1.getData().add(new XYChart.Data<>(String.valueOf(period), sale));
                series1.getData().add(new XYChart.Data<>(formattedPeriod, sale));
//                series2.getData().add(new XYChart.Data<>(String.valueOf(period), cups));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        areaChart1.getData().add(series1);
        xAxis1.setCategories(categories);
  //      areaChart1.getData().add(series2);
    }

    @FXML
    private void HandleSortByDay(ActionEvent event) {
        ratioDay.setSelected(true);
        ratioMonth.setSelected(false);
        ratioYear.setSelected(false);
        resetAreaChart();
        getDataByDay();
    }

    @FXML
    private void HandleSortByMonth(ActionEvent event) {
        ratioDay.setSelected(false);
        ratioMonth.setSelected(true);
        ratioYear.setSelected(false);
        resetAreaChart();
        getDataByMonth();
    }

    @FXML
    private void HandleSortByYear(ActionEvent event) {
        ratioDay.setSelected(false);
        ratioMonth.setSelected(false);
        ratioYear.setSelected(true);
        resetAreaChart();
        getDataByYear();
    }
    private void resetAreaChart() {
    areaChart1.getData().clear();  // Clear all series data
    xAxis1.setLabel(null);         // Reset the x-axis label
    xAxis1.setAutoRanging(true);   // Enable auto-ranging
    areaChart1.setTitle(null);     // Reset the chart title
}
}
