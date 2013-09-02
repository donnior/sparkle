Sparkle is an lightweight web framework.

## Features

### Zero Configuration

Sparkle is developend based on Servlet 3.0 specification, so it don't need `web.xml` anymore. Just include sparkle.jar in your project's classpath, start your server, it will be ready.

### Rails Like Route

Sparkle provide a Rails-like style route definition. You don't need config route using annotation. Just write your route rules like 

```java
    
    router.match("/projects/{name}").withPost().to("controller#action");
```

### Built-in JSON support.

Sparkle use a json library called `srape`. It's a smart json build library which can write same object to different json object in different scenes. Unlike Gson's annotation, it use a entity-mapping solution.

```java

    final Account account = ...;
    final int status = 20;
    
    new FieldExposerModule() {
        @Override
        public void config(FieldExposer exposer) {
            exposer.expose(status).withName("status");
            exposer.expose(account).withNameAndType("account", AccountEntity.class);
        }
    };
```    

Where `AccountEntity` defined `account`'s fields will exposed to json result.

## Sample App

* Checkout Sparkle and execute `mvn install` to install it. 
* Change directory to `sample`, execute `mvn jetty:run`





