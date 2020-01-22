# DBSOurce 数据库服务
本服务支持多数据源，支持jdbctemplate、mybatise 两种连接方式，采用config配置中心配置
## jdbcTemplate 
    参数： sql ,未进行传参处理,参数拼接到sql中。
        调用数据源 db1、db2进行区分
    
  
 


## mybatise
    mybatise 需要在xml映射中写sql ，涉及到业务， 不适合服务间调用db服务，此处只实现多数据源，未对其进行代码的梳理
 