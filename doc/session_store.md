Using Sparkle, you can define your session store strategy. 

There are several built-in SessionStore strategy you can use 

To use it you need to use Sparkle Session API, [Session](Session)

## CookieBasedSessionStore

Store session data to cookie. With this strategy Sparkle need one application-scoped `secret` to encrypt session data.

## SimpleMemorySessionStore

Store session data in memory HashMap.

## Vendor SessionStore

If you use Sparkle in servlet container and want to use servlet's session api, you can specify SessionStore to vendor's implementation.
Then Sparkle will delegate session operation to servlet. 


## Customize your SessionStore

If you like, you can implement your customize SessionStore, for example you can use Redis to manage session in a distributed environment.


