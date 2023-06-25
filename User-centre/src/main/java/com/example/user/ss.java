package com.example.user;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * @author qzz
 */
public class ss {


  //以下static final修饰的参数 如果是基于SpringBoot项目，可以在配置文件中配置，通过@Value注解获取
  /**
   * MySQL驱动 6.0以前的使用com.mysql.jdbc.Driver，6.0以后的使用com.mysql.cj.jdbc.Driver  我这边使用的是mysql-5.7.25版本的
   */
  private static final String Driver_Class_Name = "com.mysql.cj.jdbc.Driver";
  /**
   * 数据库URL   characterEncoding=UTF-8: 防止生成后文档乱码
   */
  private static final String DB_URL = "jdbc:mysql://62.234.23.101:3306/logistics?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
  /**
   * MySQL数据库账号密码
   */
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "Neu123";
  /**
   *  生成数据库文档文件路径 可根据本机电脑自行配置
   */
  private static final String FILE_OUTPUT_DIR = "E:\\dbDoc\\";

  /**
   * screw配置的文件名称，即数据库文档名称
   */
  private static final String DOC_FILE_NAME = "数据库设计文档";
  /**
   * screw配置的文件类型 HTML->HTML文件  WORD->WORD文件  MD->Markdown文件
   */
  private static final EngineFileType FILE_OUTPUT_TYPE = EngineFileType.WORD;
  /**
   * 版本
   */
  private static final String DOC_VERSION = "1.0.0";
  /**
   * 描述
   */
  private static final String DOC_DESCRIPTION = "数据库设计文档";

  public static void main(String[] args) {

    generatorDocument();
  }
  public static void generatorDocument() {

    //数据源  创建HikariConfig配置类
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(Driver_Class_Name);
    hikariConfig.setJdbcUrl(DB_URL);
    hikariConfig.setUsername(DB_USERNAME);
    hikariConfig.setPassword(DB_PASSWORD);
    //#设置useInformationSchema 可以获取tables表注释信息 即解决数据库表和列字段有说明、生成文档没有说明
    hikariConfig.addDataSourceProperty("useInformationSchema", "true");
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(5);
    DataSource dataSource = new HikariDataSource(hikariConfig);

    //创建screw的引擎配置
    EngineConfig engineConfig = EngineConfig.builder()
        //生成文件路径
        .fileOutputDir(FILE_OUTPUT_DIR)
        //打开目录
        .openOutputDir(true)
        //文件类型  HTML->HTML文件  WORD->WORD文件  MD->Markdown文件
        .fileType(FILE_OUTPUT_TYPE)
        //生成模板实现
        .produceType(EngineTemplateType.freemarker)
        //自定义文件名称，即数据库文档名称
        .fileName(DOC_FILE_NAME).build();

    //创建screw的处理配置，可忽略
    //忽略表
    ArrayList<String> ignoreTableName = new ArrayList<>();
    ignoreTableName.add("test_user");
    ignoreTableName.add("test_group");
    //忽略表前缀
    ArrayList<String> ignorePrefix = new ArrayList<>();
    ignorePrefix.add("test_");
    //忽略表后缀
    ArrayList<String> ignoreSuffix = new ArrayList<>();
    ignoreSuffix.add("_test");

    // 需要生成数据库文档的表  如果designatedTablePrefix设置值不为空，则 designatedTableName 中的表名要去掉前缀，不然会重复生成，并且表名和字段注释有误
    ArrayList<String> designatedTableName = new ArrayList<>();
//        designatedTableName.add("good");
//        designatedTableName.add("orders");
//        designatedTableName.add("buy");
    // 需要生成数据库文档的表前缀
    ArrayList<String> designatedTablePrefix = new ArrayList<>();
    designatedTablePrefix.add("");


    ProcessConfig processConfig = ProcessConfig.builder()
        //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
        //根据名称指定表生成
//                .designatedTableName(new ArrayList<>())
        .designatedTableName(designatedTableName)
        //根据表前缀生成
//                .designatedTablePrefix(new ArrayList<>())
        .designatedTablePrefix(designatedTablePrefix)
        //根据表后缀生成
        .designatedTableSuffix(new ArrayList<>())
        //忽略表名
        .ignoreTableName(ignoreTableName)
        //忽略表前缀
        .ignoreTablePrefix(ignorePrefix)
        //忽略表后缀
        .ignoreTableSuffix(ignoreSuffix).build();
    //创建screw的配置
    Configuration config = Configuration.builder()
        //版本
        .version(DOC_VERSION)
        //描述
        .description(DOC_DESCRIPTION)
        //数据源
        .dataSource(dataSource)
        //生成配置
        .engineConfig(engineConfig)
        //生成配置
        .produceConfig(processConfig)
        .build();
    //执行screw，生成数据库文档
    new DocumentationExecute(config).execute();
  }
}
