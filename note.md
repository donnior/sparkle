Action方法支持的返回类型

* String :  返回jsp视图的名称，如果以redirect：开头，则是一个重定向结果
* void:  以jsp视图渲染，框架根据request和action方法名称决定要jsp的视图名称，待实现
* JSON srape module : 一个srape module，系统自动进行json输出
* @JSON 注解的对象，如果不是一个srape module，用gson输出这个object




Action方法支持的参数类型
* @Param 以param注解的数据类型，目前只支持基本类型，
* Params ：框架重新封装的request对象，用于取参数
* HttpServletRequest：
* HttpServletResponse: 注意，如果方法传进了这个参数，表明这个方法将由用户手动管理response，框架将不负责继续渲染视图，即使方法返回类型为String之类的。


