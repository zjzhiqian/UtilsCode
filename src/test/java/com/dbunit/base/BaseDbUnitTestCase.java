package com.dbunit.base;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDbUnitTestCase extends AbstractDependencyInjectionSpringContextTests {

    @Autowired
    private DataSource dataSource;

    private IDatabaseConnection conn;

    private static final String ROOT_URL = System.getProperty("user.dir") + "/src/test/resources/";

    private static Map<String, IDataSet> xmlData = new HashMap<>();

    @Value("${db.url}")
    private String dbUrl;

    /**
     * get XML names
     */
    protected abstract List<String> getXmlName();

    protected String[] getConfigLocations() {
        return new String[]{"classpath:applicationContext.xml"};
    }

    @Override
    protected void onSetUp() throws Exception {
        // get connection
        conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        // config database as MySql
        DatabaseConfig dbConfig = conn.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        getXmlName().forEach(this::preparedDataBeforeTest);
        super.onSetUp();
    }

    /**
     * init data .include clean and insert
     */
    private void preparedDataBeforeTest(String xmlFileName) {
        xmlData.computeIfAbsent(xmlFileName, this::getDataSet); // 数据放入Map，避免同一文件重复计算
        try {
            DatabaseOperation.CLEAN_INSERT.execute(conn, xmlData.get(xmlFileName));
        } catch (DatabaseUnitException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * clean data,execute after every method
     */
    private void destroyDataAfterTest() {
        xmlData.forEach((k, v) -> {
            try {
                DatabaseOperation.DELETE_ALL.execute(conn, v);
            } catch (DatabaseUnitException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void onTearDown() throws Exception {
        destroyDataAfterTest();
        if (conn != null) {
            conn.close();
        }
        super.onTearDown();
    }

    private IDataSet getDataSet(String fileName) {
        try {
            return new FlatXmlDataSetBuilder().build(new FileInputStream(new File(ROOT_URL + fileName)));
        } catch (DataSetException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}