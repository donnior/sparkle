



## Resolvers In Sparkle

* RouteBuilderResolver
* ControllerClassResolver
* ArgumentResolver
* ViewRenderResolver


### RouteBuilderResolver

用来根据当前请求得到合适的路由，可能会有优先级考虑


### ControllerClassResolver

根据路由出来的controller name得到合适的controller class

### ArgumentResolver

处理action方法的参数

### ViewRenderResolver

根据action方法的结果和定义，获取合适的ViewRender，会有优先级考虑