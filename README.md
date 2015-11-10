[![Build Status](https://drone.io/github.com/agilej/sparkle-core/status.png)](https://drone.io/github.com/agilej/sparkle-core/latest)

Sparkle is an lightweight web framework.

## Features

### Zero Configuration

Sparkle is a tiny web framework for java. With proper plugin it can be running under Netty or a Servlet container.

* Sparkle-Servlet
It's based on Servlet 3.0 specification, so it don't need `web.xml` anymore. Just include sparkle-servlet.jar in your project's classpath, start your server, it will be ready.

* Sparkle-Netty
With this plugin, the sparkle framework can be running on Netty 4.x.

### Rails Like Route

Sparkle provide a Rails-like style route definition. You don't need config route using annotation. Just write your route rules like 

```java
    
    router.match("/projects/{name}").withPost().to("controller#action");
```

### Built-in JSON support.

Sparkle use a json library called [Jsonty][jsonty]. It's a smart json build library which can write same object to different json object in different scenes. Unlike Gson's annotation, it use a entity-mapping solution.

```java

    final Account account = ...;
    final int status = 20;
    
    new JSONModel() {
        @Override
        public void config(FieldExposer exposer) {
            exposer.expose(status).withName("status");
            exposer.expose(account).withNameAndType("account", AccountEntity.class);
        }
    };
```    

Where `AccountEntity` defined `account`'s fields will exposed to json result.

## Sample App

Checkout [sample][sparkle-sample]

[sparkle-sample]: https://github.com/donnior/sparkle-sample  "Sparkle sample project"
[jsonty]: https://github.com/agilej/jsonty  "Jsonty lib"


