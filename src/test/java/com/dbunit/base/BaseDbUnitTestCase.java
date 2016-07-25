package com.dbunit.base;

import com.google.common.collect.Maps;
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
import java.util.List;
import java.util.Map;

public abstract class BaseDbUnitTestCase extends AbstractDependencyInjectionSpringContextTests {

    @Autowired
    private DataSource dataSource;

    private IDatabaseConnection conn;

    private static final String ROOT_URL = System.getProperty("user.dir") + "/src/test/resources/";

    private static Map<String, Object> dataSetMap = Maps.newHashMap();

    @Value("${db.url}")
    private String dbUrl;

    /**
     * 子类须重写该方法，用于指定dbUnit的数据配置文件。
     * 返回该文件路径即可。
     *
     * @return 文件名
     */
    protected abstract List<String> getXmlName();

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
    private void preparedDataBeforeTest(String xmlDataFileName) {
        dataSetMap.computeIfAbsent(xmlDataFileName, this::getDataSet); // 数据放入Map，避免同一文件重复计算
        try {
            DatabaseOperation.CLEAN_INSERT.execute(conn, (IDataSet) dataSetMap.get(xmlDataFileName));
        } catch (DatabaseUnitException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * clean data,execute after every method
     */
    private void destroyDataAfterTest() {
        dataSetMap.forEach((k, v) -> {
            try {
                DatabaseOperation.DELETE_ALL.execute(conn, (IDataSet) v);
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

    private IDataSet getDataSet(String name) {
        try {
            return new FlatXmlDataSetBuilder().build(new FileInputStream(new File(ROOT_URL + name)));
        } catch (DataSetException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}