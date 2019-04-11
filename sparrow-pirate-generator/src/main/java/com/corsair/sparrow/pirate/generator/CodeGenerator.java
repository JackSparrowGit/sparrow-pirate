package com.corsair.sparrow.pirate.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author jack
 */
public class CodeGenerator {

    private static String table_prefix = "t_";

    private static String[] tables = new String[]{
              "t_sys_zuul_route"
//            "t_sys_user",
//            "t_sys_role",
//            "t_sys_permission",
//            "t_sys_group",
//            "t_oauth_client_details",
//            "t_sys_user_role",
//            "t_sys_user_group",
//            "t_sys_role_permission",
    };
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/sparrow-zuul?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static String jdbcUser = "root";
    private static String jdbcPwd = "root";
    private static String parentPack = "com.corsair.sparrow.pirate.zuul";
    private static String entityPack = "domain.bean";
    private static String superEntityClass = "com.corsair.sparrow.pirate.core.base.BaseEntity";
    private static String superControllerClass = "com.corsair.sparrow.pirate.core.base.BaseController";
    private static Boolean enableCache = false;
    private static boolean enableSuperEntity = false;


    public static void main(String[] args) {
        // todo 运行这一行即可生成代码
        generator();

    }


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static void generator() {


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();


//        String projectPath = System.getProperty("user.dir");
        String projectPath = "/Users/jack/tmp";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("jack");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setEnableCache(enableCache);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);

        gc.setDateType(DateType.ONLY_DATE);

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(jdbcUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(jdbcUser);
        dsc.setPassword(jdbcUser);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent(parentPack);
        pc.setEntity(entityPack);


        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);

        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();


//        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        if(enableSuperEntity){
            strategy.setSuperEntityClass(superEntityClass);
        }

        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(false);

        strategy.setRestControllerStyle(true);

        strategy.setSuperControllerClass(superControllerClass);

//        strategy.setInclude(scanner("表名"));
        strategy.setInclude(tables);

        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);

        strategy.setVersionFieldName("version");

//        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix(table_prefix);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
